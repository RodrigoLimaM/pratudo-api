package br.com.pratudo.recipe.model;

import br.com.pratudo.config.exception.CouldNotAnalyzeException;
import br.com.pratudo.utils.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.pt.PortugueseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
@Data
public class SummarizedRecipeWithIngredients extends SummarizedRecipe {

    private String formattedIngredients;

    public void setFormattedIngredients(List<String> selectedIngredients, List<Ingredient> ingredients) {
        PortugueseAnalyzer portugueseAnalyzer = new PortugueseAnalyzer();
        StringUtils stringUtils = new StringUtils();
        List<String> markedSelectedIngredients = new ArrayList<>();
        List<String> unmarkedSelectedIngredients = new ArrayList<>();
        List<String> analyzedSelectedIngredients = new ArrayList<>();
        selectedIngredients.forEach(selectedIngredient -> {
            try {
                analyzedSelectedIngredients.addAll(analyze(selectedIngredient, portugueseAnalyzer));
            } catch (IOException e) {
                throw new CouldNotAnalyzeException();
            }
        });

        for (Ingredient ingredient: ingredients) {
            for (IngredientItem item : ingredient.getIngredientItems()) {
                try {
                    if (CollectionUtils.containsAny(analyzedSelectedIngredients, analyze(item.getName(), portugueseAnalyzer)))
                        markedSelectedIngredients.add("*" + item.getName() + "*");
                    else
                        unmarkedSelectedIngredients.add(item.getName());
                } catch (IOException e) {
                    throw new CouldNotAnalyzeException();
                }
            }
        }

        List<String> result = Stream.concat(markedSelectedIngredients.stream(), unmarkedSelectedIngredients.stream()).distinct().collect(Collectors.toList());
        String ingredientList = null;

        if (result.size() > 10) {
            result = result.stream().limit(10).collect(Collectors.toList());
            ingredientList = "Ingredientes: " + stringUtils.convertListToString(result) + "...";
        } else {
            ingredientList = "Ingredientes: " + stringUtils.convertListToString(result) + ".";
        }

        formattedIngredients = ingredientList;
    }

    private List<String> analyze(String text, Analyzer analyzer) throws IOException {
        List<String> result = new ArrayList<>();
        TokenStream tokenStream = analyzer.tokenStream("ingredient", text);
        CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while(tokenStream.incrementToken()) {
            result.add(attr.toString());
        }
        tokenStream.close();
        return result;
    }
}