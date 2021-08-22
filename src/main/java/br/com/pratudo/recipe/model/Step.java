package br.com.pratudo.recipe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Step {

    private String step;

    @JsonProperty("items")
    private List<MethodOfPreparationItem> methodOfPreparationItem;

}
