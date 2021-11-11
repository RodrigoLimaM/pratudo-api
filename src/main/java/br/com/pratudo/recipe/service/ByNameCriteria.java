package br.com.pratudo.recipe.service;

import br.com.pratudo.recipe.model.SummarizedRecipe;
import br.com.pratudo.recipe.model.enums.Category;
import br.com.pratudo.recipe.model.mapper.RecipeMapper;
import br.com.pratudo.recipe.repository.RecipeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ByNameCriteria implements Searcher {

    private RecipeRepository recipeRepository;

    private RecipeMapper recipeMapper;

    public ByNameCriteria(RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public Page<SummarizedRecipe> getRecipesByCriteria(Pageable pageable, List<String> ingredients, String name, List<Category> categories) {
        return recipeRepository.findByName(name, pageable)
                .map(recipeMapper::convertRecipeToSummarizedRecipe);
    }
}
