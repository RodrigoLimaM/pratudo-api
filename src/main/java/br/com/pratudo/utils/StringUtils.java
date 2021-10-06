package br.com.pratudo.utils;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StringUtils {

    public String convertListToStringSeparatedWithCommas(List<String> ingredients) {
        return ingredients.toString().replace("[", "").replace("]", "");
    }
}
