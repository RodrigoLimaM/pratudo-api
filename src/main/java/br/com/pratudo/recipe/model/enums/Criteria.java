package br.com.pratudo.recipe.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Criteria {
    LATEST("Recentes", true),
    BY_INGREDIENTS(null , false)
    ;

    private String description;

    private boolean isTab;

}
