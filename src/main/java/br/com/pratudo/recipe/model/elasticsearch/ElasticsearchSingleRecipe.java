package br.com.pratudo.recipe.model.elasticsearch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ElasticsearchSingleRecipe {

    private String _id;

    @JsonProperty("_source")
    private RecipeSource recipeSource;
}
