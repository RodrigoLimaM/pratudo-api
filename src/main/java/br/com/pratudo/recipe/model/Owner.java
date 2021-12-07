package br.com.pratudo.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Owner implements Serializable {

    @Field(name = "id")
    private String id;

    @Field(name = "name")
    private String name;
}
