package br.com.pratudo.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Owner {

    @Field(name = "_id")
    private String _id;

    @Field(name = "name")
    private String name;
}
