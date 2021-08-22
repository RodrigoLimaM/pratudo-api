package br.com.pratudo.recipe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Step {

    @Field(name = "step")
    private String step;

    @JsonProperty("items")
    @Field(name = "items")
    private List<MethodOfPreparationItem> methodOfPreparationItem;

}
