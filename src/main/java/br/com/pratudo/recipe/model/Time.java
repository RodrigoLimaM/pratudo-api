package br.com.pratudo.recipe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Time implements Serializable {

    @NotNull
    @JsonProperty("unit")
    @Field(name = "unit")
    private TimeUnit timeUnit;

    @Positive
    @Field(name = "value")
    private Long value;
}
