package br.com.pratudo.recipe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ingredient {

    @NotNull
    @Field(name = "step")
    private String step;

    @Valid
    @NotEmpty
    @JsonProperty("items")
    @Field(name = "items")
    private List<IngredientItem> ingredientItems;
}
