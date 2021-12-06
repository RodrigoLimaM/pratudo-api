package br.com.pratudo.recipe.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Trend {
    LATEST("Recentes"),
    BEST_RATED("Melhor avaliados"),
    MOST_PREPARED("Mais preparados")
    ;

    private String description;

}
