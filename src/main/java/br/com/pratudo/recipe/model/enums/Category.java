package br.com.pratudo.recipe.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
    SNACKS("Lanches", "/images/snacks.txt"),
    ITALIANS("Italianas", "/images/italians.txt"),
    ARABS("Árabes", "/images/arabics.txt"),
    BRAZILIANS("Brasileiras", "/images/brazilians.txt"),
    JAPANESES("Japonesas", "/images/japaneses.txt"),
    CHINESE("Chinesas", "/images/chinese.txt"),
    VEGETARIANS("Vegetarianas", "/images/vegetarians.txt"),
    DRINKS("Bebidas", "/images/drinks.txt"),
    DESSERTS("Sobremesas", "/images/desserts.txt"),
    OTHERS("Outros", "/images/others.txt")
    ;

    private String description;

    private String imageFilePath;
}
