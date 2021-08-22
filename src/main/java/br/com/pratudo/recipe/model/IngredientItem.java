package br.com.pratudo.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IngredientItem {

    @Field(name = "name")
    private String name;

    @Field(name = "portion")
    private String portion;
}
