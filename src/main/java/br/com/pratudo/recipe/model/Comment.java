package br.com.pratudo.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Comment {

    @Field(name = "id")
    private String id;

    @Field(name = "owner")
    private String owner;

    @Field(name = "content")
    private String content;

    @Field(type = FieldType.Date, name = "creationDate", format = {}, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime creationDate;

    @Field(name = "replies")
    private List<Reply> replies;
}
