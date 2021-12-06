package br.com.pratudo.recipe.service;

import br.com.pratudo.recipe.model.SummarizedRecipe;
import br.com.pratudo.recipe.model.mapper.RecipeMapper;
import br.com.pratudo.recipe.repository.RecipeTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class BestRatedCriteria implements Searcher {

    private RecipeTemplateRepository recipeTemplateRepository;

    private RecipeMapper recipeMapper;

    public static final String FIELD = "ratings.rating";

    @Autowired
    public BestRatedCriteria(RecipeTemplateRepository recipeTemplateRepository, RecipeMapper recipeMapper) {
        this.recipeTemplateRepository = recipeTemplateRepository;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public Page<SummarizedRecipe> getRecipesByTrend(Pageable pageable) {
        return recipeTemplateRepository.findAllByOrderByListFieldAverageCountDesc(pageable, FIELD)
                .map(recipeMapper::convertRecipeToSummarizedRecipe);
    }
}
