package br.com.pratudo.recipe.model;

import br.com.pratudo.recipe.model.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Recipe {

    private String _id;

    private String name;

    private List<String> images;

    private Owner owner;

    private Difficulty difficulty;

    private LocalDate creationDate;

    private String chefTips;

    private BigDecimal rate;

    private List<Rating> ratings;

    private List<Ingredient> ingredients;

    private MethodOfPreparation methodOfPreparation;

    private List<Comment> comments;

    private List<String> tags;
}
