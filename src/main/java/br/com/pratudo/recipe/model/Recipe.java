package br.com.pratudo.recipe.model;

import br.com.pratudo.recipe.model.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(indexName = "recipes")
public class Recipe {

    @Id
    private String _id;

    @Field(name = "name")
    private String name;

    @Field(name = "images")
    private List<String> images;

    @Field(name = "owner")
    private Owner owner;

    @Field(name = "difficulty")
    private Difficulty difficulty;

    @Field(name = "creationDate")
    private LocalDate creationDate;

    @Field(name = "chefTips")
    private String chefTips;

    @Field(name = "rate")
    private BigDecimal rate;

    @Field(name = "ratings")
    private List<Rating> ratings;


    @Field(name = "ingredients")
    private List<Ingredient> ingredients;

    @Field(name = "methodOfPreparation")
    private MethodOfPreparation methodOfPreparation;

    @Field(name = "comments")
    private List<Comment> comments;

    @Field(name = "tags")
    private List<String> tags;
}
