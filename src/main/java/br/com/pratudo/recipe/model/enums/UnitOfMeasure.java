package br.com.pratudo.recipe.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UnitOfMeasure {

    CUP_OF_COFFEE("Xícara de café", "xcf"),
    TEA_CUP("Xícara de chá", "xíc"),
    COFFEE_SPOON("Colher de café", "ccf"),
    TEA_SPOON("Colher de chá", "cc"),
    DESERT_SPOON("Colher de sobremesa", "csb"),
    SOUP_SPOON("Colher de sopa", "csp"),
    CUP("Copo", "cp"),
    PINCH("Pitada", "pit"),
    UNIT("Unidade", "uni"),
    PACKAGE("Pacote", "pc"),
    MILLILITER("Litro", "l"),
    KILOGRAM("Kilograma", "kg"),
    GRAM("Grama", "g")
    ;

    private String translation;

    private String abbreviation;
}
