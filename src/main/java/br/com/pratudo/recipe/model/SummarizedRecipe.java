package br.com.pratudo.recipe.model;

import br.com.pratudo.recipe.model.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SummarizedRecipe {

    private String _id;

    private String name;

    @ToString.Exclude
    private List<String> images;

    private Owner owner;

    private Difficulty difficulty;

    private Integer serves;

    private Double rate;

    private Boolean isNew;

    private Time totalMethodOfPreparationTime;
}
