package br.com.pratudo.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MethodOfPreparationItem {

    @Field(name = "description")
    private String description;

    @Field(name = "time")
    private Time time;
}
