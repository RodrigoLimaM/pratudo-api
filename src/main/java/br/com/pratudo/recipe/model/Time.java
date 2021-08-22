package br.com.pratudo.recipe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Time {

    @JsonProperty("unit")
    private TimeUnit timeUnit;

    @Positive
    private Long value;
}
