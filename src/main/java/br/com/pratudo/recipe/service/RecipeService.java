package br.com.pratudo.recipe.service;

import br.com.pratudo.commons.gamification.GamificationContext;
import br.com.pratudo.commons.gamification.GamificationHandlerByCriteriaFactory;
import br.com.pratudo.config.exception.CouldNotAnalyzeException;
import br.com.pratudo.recipe.model.CategorieValues;
import br.com.pratudo.recipe.model.GamificationData;
import br.com.pratudo.recipe.model.Ingredient;
import br.com.pratudo.recipe.model.IngredientItem;
import br.com.pratudo.recipe.model.Owner;
import br.com.pratudo.recipe.model.Recipe;
import br.com.pratudo.recipe.model.SummarizedRecipe;
import br.com.pratudo.recipe.model.SummarizedRecipeWithIngredients;
import br.com.pratudo.recipe.model.UnitOfMeasureValues;
import br.com.pratudo.recipe.model.dto.RecipeDTO;
import br.com.pratudo.recipe.model.enums.Category;
import br.com.pratudo.recipe.model.enums.Difficulty;
import br.com.pratudo.recipe.model.enums.Trend;
import br.com.pratudo.recipe.model.enums.UnitOfMeasure;
import br.com.pratudo.recipe.model.mapper.RecipeMapper;
import br.com.pratudo.recipe.repository.RecipeRepository;
import br.com.pratudo.recipe.repository.RecipeTemplateRepository;
import br.com.pratudo.commons.utils.SecurityUtils;
import br.com.pratudo.commons.utils.StringUtils;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeTemplateRepository recipeTemplateRepository;

    @Autowired
    RecipeMapper recipeMapper;

    @Autowired
    SecurityUtils securityUtils;

    @Autowired
    StringUtils stringUtils;

    @Autowired
    AnalyzerService analyzerService;

    @Autowired
    SearcherByTrendFactory searcherByTrendFactory;

    @Autowired
    RatingService ratingService;

    @Autowired
    GamificationHandlerByCriteriaFactory gamificationHandlerByCriteriaFactory;

    private static final String INGREDIENT_MARKER = "*";

    private static final Integer FORMATTED_INGREDIENTS_ITEMS_SIZE = 10;

    @CacheEvict(cacheNames = "RacipesByTrend", allEntries = true)
    public Recipe createRecipe(final RecipeDTO recipeDTO) {
        recipeDTO.setOwner(buildInitialOwner());

        recipeDTO.setCreationDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        recipeDTO.setRatings(Collections.emptyList());

        recipeDTO.setPreparations(0);

        recipeDTO.setComments(Collections.emptyList());

        return recipeRepository.save(recipeMapper.convertRecipeDTOToRecipe(recipeDTO));
    }

    private Owner buildInitialOwner() {
        return Owner.builder()
                .id(securityUtils.getCurrent_Id())
                .name(securityUtils.getCurrentName())
                .build();
    }

    public Optional<Recipe> getRecipeById(String _id) {
        Optional<Recipe> recipe = recipeRepository.findById(_id);

        return recipe.map(rec -> rec.buildRecipeWithIsUserAllowedToRate(ratingService.IsUserAllowedToRate(rec, securityUtils.getCurrent_Id())))
                .map(Recipe::buildRecipeWithTranslatedCategory);
    }

    @Cacheable(cacheNames = "RacipesByTrend", key = "{#pageable, #trend}")
    public Page<SummarizedRecipe> getRecipesByTrend(Pageable pageable, Trend trend) {
        System.out.println("**************************\n" +"BATEU");
        return searcherByTrendFactory.getUserIdByTypeInstance(trend)
                .getRecipesByTrend(pageable);
    }

    public Page<SummarizedRecipeWithIngredients> getRecipesByQuery(Pageable pageable, List<Category> categories, List<Difficulty> difficulties, Long serves, String name, List<String> ingredients) {
        return recipeTemplateRepository.getRecipeByQuery(pageable, categories, difficulties, serves, name, ingredients)
                .map(recipe -> recipeMapper.convertRecipeToSummarizedRecipeWithIngredients(recipe, getFormattedIngredients(ingredients, recipe.getIngredients())));
    }

    private String getFormattedIngredients(List<String> selectedIngredients, List<Ingredient> totalIngredients) {
        if (Objects.isNull(selectedIngredients) || selectedIngredients.isEmpty())
            return null;

        List<String> markedSelectedIngredients = new ArrayList<>();
        List<String> unmarkedSelectedIngredients = new ArrayList<>();
        List<String> analyzedSelectedIngredients = new ArrayList<>();

        selectedIngredients.forEach(selectedIngredient -> {
            try {
                analyzedSelectedIngredients.addAll(analyzerService.analyze(selectedIngredient));
            } catch (IOException e) {
                throw new CouldNotAnalyzeException();
            }
        });

        totalIngredients.forEach(ingredient -> ingredient.getIngredientItems()
                .forEach(item -> {
                    try {
                        if (containsAnyItemFrom(analyzedSelectedIngredients, item))
                            markedSelectedIngredients.add(INGREDIENT_MARKER + item.getName() + INGREDIENT_MARKER);
                        else
                            unmarkedSelectedIngredients.add(item.getName());
                    } catch (IOException e) {
                        throw new CouldNotAnalyzeException();
                    }
                }));

        List<String> result = Stream.concat(markedSelectedIngredients.stream(),
                        unmarkedSelectedIngredients.stream())
                .distinct()
                .collect(Collectors.toList());

        return formatIngredients(result);
    }

    private boolean containsAnyItemFrom(List<String> analyzedSelectedIngredients, IngredientItem item) throws IOException {
        return CollectionUtils.containsAny(analyzedSelectedIngredients, analyzerService.analyze(item.getName()));
    }

    private String formatIngredients(List<String> result) {
        String ingredientList;
        if (result.size() > FORMATTED_INGREDIENTS_ITEMS_SIZE) {
            result = result.stream().limit(FORMATTED_INGREDIENTS_ITEMS_SIZE).collect(Collectors.toList());
            ingredientList = "Ingredientes: " + stringUtils.convertListToStringSeparatedWithCommas(result) + "...";
        } else {
            ingredientList = "Ingredientes: " + stringUtils.convertListToStringSeparatedWithCommas(result) + ".";
        }
        return ingredientList;
    }

    public List<CategorieValues> getCategories() {
        List<CategorieValues> categoriesValues = new ArrayList<>();

         Arrays.stream(Category.values())
                 .forEach(category -> categoriesValues.add(CategorieValues.builder()
                         .key(category.name())
                         .value(category.getDescription())
                         .image(getCategoryImageFilePath(category.getImageFilePath()))
                         .build())
                 );

         return categoriesValues;
    }

    @SneakyThrows
    private String getCategoryImageFilePath(String imageFilePath) {
        return IOUtils.toString(RecipeService.class.getResourceAsStream(imageFilePath));
    }

    public List<UnitOfMeasureValues> getUnitsOfMeasure() {
        List<UnitOfMeasureValues> unitOfMeasureValues = new ArrayList<>();

        Arrays.stream(UnitOfMeasure.values())
                .forEach(unitOfMeasure -> unitOfMeasureValues.add(
                                UnitOfMeasureValues.builder()
                                        .key(unitOfMeasure.name())
                                        .translate(unitOfMeasure.getTranslation())
                                        .abbreviation(unitOfMeasure.getAbbreviation())
                                        .build()
                        )
                );

        return unitOfMeasureValues;
    }

    public Page<SummarizedRecipe> getMyRecipes(Pageable pageable) {
        return recipeRepository.findByOwner_Id(securityUtils.getCurrent_Id(), pageable)
                .map(recipeMapper::convertRecipeToSummarizedRecipe);
    }

    public GamificationData handleCreateRecipeGamification() {
        return gamificationHandlerByCriteriaFactory.getGamificationHandlerByGamificationContextInstance(GamificationContext.CREATE_RECIPE)
                .handleGamification();
    }
}
