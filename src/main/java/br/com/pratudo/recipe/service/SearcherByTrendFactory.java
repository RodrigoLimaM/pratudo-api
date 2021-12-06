package br.com.pratudo.recipe.service;

import br.com.pratudo.recipe.model.enums.Trend;
import br.com.pratudo.recipe.model.mapper.RecipeMapper;
import br.com.pratudo.recipe.repository.RecipeRepository;
import br.com.pratudo.recipe.repository.RecipeTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class SearcherByTrendFactory {

    private static final Map<Trend, Searcher> TREND_BY_SEARCHER_MAP = new EnumMap<>(Trend.class);

    @Autowired
    public SearcherByTrendFactory(RecipeRepository recipeRepository,
                                  RecipeMapper recipeMapper,
                                  RecipeTemplateRepository recipeTemplateRepository) {
        TREND_BY_SEARCHER_MAP.put(Trend.LATEST, new LatestCriteria(recipeRepository, recipeMapper));
        TREND_BY_SEARCHER_MAP.put(Trend.MOST_PREPARED, new MostPreparedCriteria(recipeTemplateRepository, recipeMapper));
        TREND_BY_SEARCHER_MAP.put(Trend.BEST_RATED, new BestRatedCriteria(recipeTemplateRepository, recipeMapper));
    }

    public Searcher getUserIdByTypeInstance(Trend trend) {
        return TREND_BY_SEARCHER_MAP.get(trend);
    }

}
