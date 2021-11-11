package br.com.pratudo.recipe.service;

import br.com.pratudo.recipe.model.SummarizedRecipe;
import br.com.pratudo.recipe.model.mapper.RecipeMapper;
import br.com.pratudo.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class LatestCriteria implements Searcher {

    private RecipeRepository recipeRepository;

    private RecipeMapper recipeMapper;

    @Autowired
    public LatestCriteria(RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public Page<SummarizedRecipe> getRecipesByTrend(Pageable pageable) {
        return recipeRepository.findAllByOrderByCreationDateDesc(pageable)
                .map(recipeMapper::convertRecipeToSummarizedRecipe);
    }
}
