package br.com.pratudo.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UnitOfMeasureValues {

    private String key;

    private String translate;

    private String abbreviation;
}
