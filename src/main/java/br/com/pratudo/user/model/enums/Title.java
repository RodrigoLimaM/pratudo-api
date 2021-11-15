package br.com.pratudo.user.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Title {
    INICIANTE("Iniciante"),
    CHEF("Chefe de cozinha"),
    MESTRE_CUCA("Mestre cuca")
    ;

    private String description;
}
