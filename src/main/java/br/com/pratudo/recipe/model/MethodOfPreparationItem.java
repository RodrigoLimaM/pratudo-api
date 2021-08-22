package br.com.pratudo.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MethodOfPreparationItem {

    @NotNull
    @Field(name = "description")
    private String description;

    @Valid
    @NotNull
    @Field(name = "time")
    private Time time;
}
