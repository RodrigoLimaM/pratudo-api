package br.com.pratudo.recipe.model.mapper;

import br.com.pratudo.recipe.model.Ingredient;
import br.com.pratudo.recipe.model.Recipe;
import br.com.pratudo.recipe.model.SummarizedRecipe;
import br.com.pratudo.recipe.model.SummarizedRecipeWithIngredients;
import br.com.pratudo.recipe.model.dto.RecipeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeMapper {

    @Autowired
    ModelMapper modelMapper;

    public Recipe convertRecipeDTOToRecipe(RecipeDTO recipeDTO) {
        return modelMapper.map(recipeDTO, Recipe.class);
    }

    public SummarizedRecipe convertRecipeToSummarizedRecipe(Recipe recipe) {
        SummarizedRecipe summarizedRecipe = modelMapper.map(recipe, SummarizedRecipe.class);
        summarizedRecipe.setRate(Recipe.getRate(recipe.getRatings()));
        summarizedRecipe.setIsNew(Recipe.isNew(recipe.getCreationDate()));
        summarizedRecipe.setTotalMethodOfPreparationTime(Recipe.getTotalMethodOfPreparationTime(recipe.getMethodOfPreparation()));

        return summarizedRecipe;
    }

    public SummarizedRecipeWithIngredients convertRecipeToSummarizedRecipeWithIngredients(Recipe recipe, List<String> selectedIngredients, List<Ingredient> ingredients) {
        SummarizedRecipeWithIngredients summarizedRecipeWithIngredients = modelMapper.map(recipe, SummarizedRecipeWithIngredients.class);
        summarizedRecipeWithIngredients.setRate(Recipe.getRate(recipe.getRatings()));
        summarizedRecipeWithIngredients.setIsNew(Recipe.isNew(recipe.getCreationDate()));
        summarizedRecipeWithIngredients.setTotalMethodOfPreparationTime(Recipe.getTotalMethodOfPreparationTime(recipe.getMethodOfPreparation()));
        summarizedRecipeWithIngredients.setFormattedIngredients(selectedIngredients, ingredients);
        return summarizedRecipeWithIngredients;
    }
}
