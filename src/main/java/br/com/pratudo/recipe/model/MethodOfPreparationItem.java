package br.com.pratudo.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MethodOfPreparationItem {

    private String description;

    private Time time;
}
