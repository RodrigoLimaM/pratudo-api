package br.com.pratudo.recipe.service;

import br.com.pratudo.config.exception.CouldNotAnalyzeException;
import br.com.pratudo.recipe.model.Ingredient;
import br.com.pratudo.recipe.model.IngredientItem;
import br.com.pratudo.recipe.model.SummarizedRecipe;
import br.com.pratudo.recipe.model.enums.Category;
import br.com.pratudo.recipe.model.mapper.RecipeMapper;
import br.com.pratudo.recipe.repository.RecipeRepository;
import br.com.pratudo.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
class ByIngredientsCriteria implements Searcher {

    private RecipeRepository recipeRepository;

    private RecipeMapper recipeMapper;

    private StringUtils stringUtils;

    private AnalyzerService analyzerService;

    private static final String INGREDIENT_MARKER = "*";

    private static final Integer FORMATTED_INGREDIENTS_ITEMS_SIZE = 10;

    @Autowired
    public ByIngredientsCriteria(RecipeRepository recipeRepository, RecipeMapper recipeMapper, StringUtils stringUtils, AnalyzerService analyzerService) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
        this.stringUtils = stringUtils;
        this.analyzerService = analyzerService;
    }

    @Override
    public Page<SummarizedRecipe> getRecipesByCriteria(Pageable pageable, List<String> ingredients, String name, List<Category> categories) {
        return recipeRepository.findByIngredients(stringUtils.convertListToStringSeparatedWithCommas(ingredients), pageable)
                .map(recipe -> recipeMapper.convertRecipeToSummarizedRecipeWithIngredients(recipe, getFormattedIngredients(ingredients, recipe.getIngredients())));
    }

    private String getFormattedIngredients(List<String> selectedIngredients, List<Ingredient> totalIngredients) {
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
}
