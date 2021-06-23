package br.com.pratudo.commons.search;

import static br.com.pratudo.commons.search.SearchParams.Match;
import static br.com.pratudo.commons.search.SearchParams.Query;
import static br.com.pratudo.commons.search.SearchParams.builder;

public class SearchParamsFactory {

    private SearchParamsFactory() {

    }

    public static SearchParams buildGetUserByEmailParams(String email) {
        return builder()
                .size(1)
                .query(Query.builder()
                        .match(Match.builder()
                                .email(email)
                                .build())
                        .build())
                .build();
    }
}
