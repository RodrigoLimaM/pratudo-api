package br.com.pratudo.recipe.service;

import br.com.pratudo.recipe.model.enums.Trend;
import br.com.pratudo.recipe.model.mapper.RecipeMapper;
import br.com.pratudo.recipe.repository.RecipeRepository;
import br.com.pratudo.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class SearcherByCriteriaFactory {

    private static final Map<Trend, Searcher> CRITERIA_BY_SEARCHER_MAP = new EnumMap<>(Trend.class);

    @Autowired
    public SearcherByCriteriaFactory(RecipeRepository recipeRepository,
                                     RecipeMapper recipeMapper,
                                     StringUtils stringUtils,
                                     AnalyzerService analyzerService) {
        CRITERIA_BY_SEARCHER_MAP.put(Trend.LATEST, new LatestCriteria(recipeRepository, recipeMapper));
    }

    public Searcher getUserIdByTypeInstance(Trend trend) {
        return CRITERIA_BY_SEARCHER_MAP.get(trend);
    }

}
