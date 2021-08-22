package br.com.pratudo.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reply {

    private String owner;

    private String content;

    private LocalDate creationDate;
}
