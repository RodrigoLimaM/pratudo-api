package br.com.pratudo.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rating {

    @Field(name = "owner")
    private String owner;

    @Field(name = "rating")
    private Long rating;
}
