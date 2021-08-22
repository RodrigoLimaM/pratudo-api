package br.com.pratudo.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reply {

    @Field(name = "owner")
    private String owner;

    @Field(name = "content")
    private String content;

    @Field(type = FieldType.Date, name = "creationDate")
    private LocalDate creationDate;
}
