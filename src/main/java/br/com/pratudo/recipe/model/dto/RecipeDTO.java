package br.com.pratudo.recipe.model.dto;

import br.com.pratudo.recipe.model.Comment;
import br.com.pratudo.recipe.model.Ingredient;
import br.com.pratudo.recipe.model.MethodOfPreparation;
import br.com.pratudo.recipe.model.Owner;
import br.com.pratudo.recipe.model.Rating;
import br.com.pratudo.recipe.model.enums.Category;
import br.com.pratudo.recipe.model.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeDTO {

    @NotNull
    private String name;

    private List<String> images;

    private Owner owner;

    @NotNull
    private Difficulty difficulty;

    @NotNull
    private Integer serves;

    private LocalDateTime creationDate;

    @NotNull
    private String chefTips;

    private List<Rating> ratings;

    @Valid
    @NotEmpty
    private List<Ingredient> ingredients;

    @Valid
    @NotNull
    private MethodOfPreparation methodOfPreparation;

    private List<Comment> comments;

    private List<String> tags;

    @NotEmpty
    private List<Category> categories;
}
