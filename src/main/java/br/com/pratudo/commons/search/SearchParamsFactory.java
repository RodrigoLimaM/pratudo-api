package br.com.pratudo.commons.search;

import java.util.List;

import static br.com.pratudo.commons.search.SearchParams.Match;
import static br.com.pratudo.commons.search.SearchParams.Query;

public class SearchParamsFactory {

    private SearchParamsFactory() {

    }

    public static SearchParams buildGetUserByEmailParams(String email) {
        return SearchParams.builder()
                .size(1)
                .query(Query.builder()
                        .match(Match.builder()
                                .email(email)
                                .build())
                        .build())
                .build();
    }

    public static SearchParams buildGetRecipesByIngredientsParams(List<String> ingredients) {
        return  SearchParams.builder()
                .query(Query.builder()
                        .match(Match.builder()
                                .ingredientsItemsName(ingredients.toString().replace("[", "").replace("]", ""))
                                .build())
                        .build())
                .build();
    }

    public static SearchParams buildGetRecipesByNameParams(String name) {
        return  SearchParams.builder()
                .query(Query.builder()
                        .match(Match.builder()
                                .name(name)
                                .build())
                        .build())
                .build();
    }
}
