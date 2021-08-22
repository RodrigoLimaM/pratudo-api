package br.com.pratudo.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {

    private String owner;

    private String content;

    private LocalDate localDate;

    private List<Reply> replies;
}
