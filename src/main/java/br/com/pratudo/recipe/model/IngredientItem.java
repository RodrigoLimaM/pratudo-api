package br.com.pratudo.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IngredientItem {

    @NotNull
    @Field(name = "name")
    private String name;

    @NotNull
    @Field(name = "portion")
    private String portion;
}
