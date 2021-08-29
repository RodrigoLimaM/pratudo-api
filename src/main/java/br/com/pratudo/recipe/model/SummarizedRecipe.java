package br.com.pratudo.recipe.model;

import br.com.pratudo.recipe.model.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SummarizedRecipe {

    private String _id;

    private String name;

    private List<String> images;

    private Owner owner;

    private Difficulty difficulty;

    private Double rate;
}
