package br.com.pratudo.recipe.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
    SNACK("Lanche"),
    ITALIAN("Italiana"),
    ARABIC("Árabe"),
    BRAZILIAN("Brasileira"),
    JAPANESE("Japonesa"),
    CHINESE("Chinesa"),
    VEGETARIAN("Vegetariano"),
    DRINKS("Bebidas"),
    CANDIES("Doces"),
    OTHER("Outros")
    ;

    private String description;
}
