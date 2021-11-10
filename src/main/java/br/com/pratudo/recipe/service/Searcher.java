package br.com.pratudo.recipe.service;

import br.com.pratudo.recipe.model.SummarizedRecipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Searcher {

    Page<SummarizedRecipe> getRecipesByCriteria(Pageable pageable, List<String> ingredients);
}
