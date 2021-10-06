package br.com.pratudo.utils;

import br.com.pratudo.recipe.model.enums.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StringUtils {

    public String convertListToStringSeparatedWithCommas(List<String> ingredients) {
        return ingredients.toString().replace("[", "").replace("]", "");
    }

    public String convertListToStringWithQuotationMarks(List<Category> categories) {
        return categories.stream().map(category -> "\"" +category +"\"")
                .collect(Collectors.toList()).toString();
    }
}
