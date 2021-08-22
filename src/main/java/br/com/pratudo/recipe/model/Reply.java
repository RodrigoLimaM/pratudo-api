package br.com.pratudo.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reply {

    @Field(name = "owner")
    private String owner;

    @Field(name = "content")
    private String content;

    @Field(name = "name")
    private LocalDate creationDate;
}
