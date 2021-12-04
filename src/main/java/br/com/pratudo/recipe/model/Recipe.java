package br.com.pratudo.recipe.model;

import br.com.pratudo.recipe.model.enums.Category;
import br.com.pratudo.recipe.model.enums.Difficulty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(indexName = "recipes")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Recipe {

    @Id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String _id;

    @Field(name = "name")
    private String name;

    @Field(name = "images")
    @ToString.Exclude
    private List<String> images;

    @Field(name = "owner")
    private Owner owner;

    @Field(name = "difficulty")
    private Difficulty difficulty;

    @Field(name = "serves")
    private Integer serves;

    @Field(type = FieldType.Date, name = "creationDate", format = {}, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime creationDate;

    @JsonProperty("isNew")
    private Boolean isNew() {
        return isNew(creationDate);
    }

    @Field(name = "chefTips")
    private String chefTips;

    @JsonProperty
    private Double getRate() {
        return getRate(ratings);
    }

    @Field(name = "ratings")
    private List<Rating> ratings;

    @Field(name = "preparations")
    private Integer preparations;

    private Boolean isUserAllowedToRate;

    public Recipe buildRecipeWithIsUserAllowedToRate(boolean isUserAllowedToRate) {
        this.isUserAllowedToRate = isUserAllowedToRate;

        return this;
    }

    @Field(name = "ingredients")
    private List<Ingredient> ingredients;

    @Field(name = "methodOfPreparation")
    private MethodOfPreparation methodOfPreparation;

    @JsonProperty
    public Time getTotalMethodOfPreparationTime() {
        return getTotalMethodOfPreparationTime(methodOfPreparation);
    }

    @Field(name = "comments")
    private List<Comment> comments;

    @Field(name = "tags")
    private List<String> tags;

    @Field(name = "categories")
    private List<Category> categories;

    private List<String> categoriesValues;

    public Recipe buildRecipeWithTranslatedCategory() {
        this.categoriesValues = categories.stream()
                .map(Category::getDescription)
                .collect(Collectors.toList());

        this.categories = null;

        return this;
    }

    public static Double getRate(List<Rating> ratings) {
        return ratings.stream()
                .map(Rating::getRating)
                .map(Objects::requireNonNull)
                .mapToDouble(value -> value)
                .average()
                .orElse(0.0);
    }

    public static Boolean isNew(LocalDateTime creationDate) {
        return ChronoUnit.DAYS.between(creationDate, LocalDateTime.now()) < 4;
    }

    public static Time getTotalMethodOfPreparationTime(MethodOfPreparation methodOfPreparation) {
        return Time.builder()
                .value(
                        methodOfPreparation.getSteps().stream()
                                .map(Step::getTime)
                                .map(stepTime -> stepTime.getTimeUnit().toMinutes(stepTime.getValue()))
                                .reduce(0L, Long::sum)
                )
                .timeUnit(TimeUnit.MINUTES)
                .build();
    }
}
