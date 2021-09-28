package br.com.pratudo.recipe.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SummarizedRecipeWithIngredients extends SummarizedRecipe {

    private String formattedIngredients;
}