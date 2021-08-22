package br.com.pratudo.recipe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {

    @Field(name = "owner")
    private String owner;

    @Field(name = "content")
    private String content;

    @Field(type = FieldType.Date, name = "creationDate")
    private LocalDate creationDate;

    @Field(name = "replies")
    private List<Reply> replies;
}
