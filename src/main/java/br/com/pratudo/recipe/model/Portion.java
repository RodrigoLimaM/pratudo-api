package br.com.pratudo.recipe.model;

import br.com.pratudo.recipe.model.enums.UnitOfMeasure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Portion {

    @NotNull
    @Field(name = "value")
    private String value;

    @NotNull
    @Field(name = "unitOfMeasure")
    private UnitOfMeasure unitOfMeasure;
}
