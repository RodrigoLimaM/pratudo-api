package br.com.pratudo.recipe.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Trend {
    LATEST("Recentes"),
    MOST_PREPARATED("Mais preparados"),
//    MOST_COMMENTED("Mais comentados")
     ;

    private String description;

}
