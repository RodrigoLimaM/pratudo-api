package br.com.pratudo.recipe.service;

import br.com.pratudo.recipe.model.SummarizedRecipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Searcher {

    Page<SummarizedRecipe> getRecipesByTrend(Pageable pageable);
}
