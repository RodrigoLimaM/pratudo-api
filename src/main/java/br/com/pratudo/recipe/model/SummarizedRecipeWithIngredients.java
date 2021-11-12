package br.com.pratudo.recipe.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SummarizedRecipeWithIngredients extends SummarizedRecipe {

    private String formattedIngredients;
}