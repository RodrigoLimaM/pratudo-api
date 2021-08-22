package br.com.pratudo.recipe.model.elasticsearch;

import br.com.pratudo.recipe.model.Comment;
import br.com.pratudo.recipe.model.Ingredient;
import br.com.pratudo.recipe.model.MethodOfPreparation;
import br.com.pratudo.recipe.model.Owner;
import br.com.pratudo.recipe.model.Rating;
import br.com.pratudo.recipe.model.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeSource {

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
