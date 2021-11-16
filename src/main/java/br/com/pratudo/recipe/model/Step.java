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
public class Step {

    @NotNull
    @Field(name = "step")
    private String step;

    @Valid
    @NotNull
    @Field(name = "time")
    private Time time;

    @Valid
    @NotEmpty
    @JsonProperty("items")
    @Field(name = "items")
    private List<MethodOfPreparationItem> methodOfPreparationItem;

}
