package br.com.pratudo.recipe.service;

import br.com.pratudo.config.exception.CouldNotAnalyzeException;
import br.com.pratudo.recipe.model.CategorieValues;
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
import br.com.pratudo.utils.SecurityUtils;
import br.com.pratudo.utils.StringUtils;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.time.LocalDateTime;
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
    SearcherByCriteriaFactory searcherByCriteriaFactory;

    private static final String INGREDIENT_MARKER = "*";

    private static final Integer FORMATTED_INGREDIENTS_ITEMS_SIZE = 10;

    public Recipe createRecipe(final RecipeDTO recipeDTO) {
        recipeDTO.setOwner(buildInitialOwner());

        recipeDTO.setCreationDate(LocalDateTime.now());

        recipeDTO.setRatings(Collections.emptyList());

        recipeDTO.setComments(Collections.emptyList());

        return recipeRepository.save(recipeMapper.convertRecipeDTOToRecipe(recipeDTO));
    }

    private Owner buildInitialOwner() {
        return Owner.builder()
                ._id(securityUtils.getCurrent_Id())
                .name(securityUtils.getCurrentName())
                .build();
    }

    public Optional<Recipe> getRecipeById(String _id) {
        return recipeRepository.findById(_id);
    }

    public Page<SummarizedRecipe> getRecipesByTrend(Pageable pageable, Trend trend) {
        return searcherByCriteriaFactory.getUserIdByTypeInstance(trend)
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
                 .forEach(category -> {
                             try {
                                 categoriesValues.add(CategorieValues.builder()
                                         .key(category.name())
                                        .value(category.getDescription())
                                        .image(Files.asCharSource(new ClassPathResource(category.getImageFilePath()).getFile(), Charsets.UTF_8).readFirstLine())
                                        .build());
                             } catch (IOException e) {
                                 e.printStackTrace();
                             }
                         }
                 );

         return categoriesValues;
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
}
