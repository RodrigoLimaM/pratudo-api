package br.com.pratudo.recipe.service;

import br.com.pratudo.recipe.model.enums.Criteria;
import br.com.pratudo.recipe.model.mapper.RecipeMapper;
import br.com.pratudo.recipe.repository.RecipeRepository;
import br.com.pratudo.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class SearcherByCriteria {

    private static final Map<Criteria, Searcher> CRITERIA_BY_SEARCHER_MAP = new EnumMap<>(Criteria.class);

    @Autowired
    public SearcherByCriteria(RecipeRepository recipeRepository,
                              RecipeMapper recipeMapper,
                              StringUtils stringUtils,
                              AnalyzerService analyzerService) {
        CRITERIA_BY_SEARCHER_MAP.put(Criteria.LATEST, new LatestCriteria(recipeRepository, recipeMapper));
        CRITERIA_BY_SEARCHER_MAP.put(Criteria.BY_INGREDIENTS, new ByIngredientsCriteria(recipeRepository, recipeMapper, stringUtils, analyzerService));
    }

    public Searcher getUserIdByTypeInstance(Criteria criteria) {
        return CRITERIA_BY_SEARCHER_MAP.get(criteria);
    }

}
