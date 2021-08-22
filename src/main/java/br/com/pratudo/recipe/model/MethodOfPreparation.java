package br.com.pratudo.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MethodOfPreparation {

    @JsonProperty("totalMethodOfPreparationTime")
    @Field(name = "totalMethodOfPreparationTime")
    private Time time;

    @Field(name = "steps")
    private List<Step> steps;

    @JsonIgnore
    public Long getTotalMinutesInMethodOfPreparation() {

        return steps.stream()
                .map(
                        step -> step.getMethodOfPreparationItem()
                                .stream()
                                .map(MethodOfPreparationItem::getTime)
                                .map(stepTime -> stepTime.getTimeUnit().toMinutes(stepTime.getValue()))
                                .reduce(0L, Long::sum)
                )
                .reduce(0L, Long::sum);
    }
}
