package br.com.pratudo.recipe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ingredient {

    private String step;

    @JsonProperty("items")
    private List<IngredientItem> ingredientItems;
}
