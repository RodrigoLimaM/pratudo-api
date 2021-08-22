package br.com.pratudo.recipe.model.elasticsearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeHit {

    private String _id;

    private BigDecimal _score;

    @JsonProperty("_source")
    private RecipeSource recipeSource;
}
