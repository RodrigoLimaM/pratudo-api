package br.com.pratudo.recipe.repository;

import br.com.pratudo.recipe.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface RecipeRepository extends ElasticsearchRepository<Recipe, String> {

    Page<Recipe> findByName(String name, Pageable pageable);

    @Override
    Page<Recipe> findAll(Pageable pageable);

    @Query("{\"match\":{\"ingredients.items.name\":{\"query\":\"?0\"}}}")
    Page<Recipe> findByIngredients(String ingredients, Pageable pageable);
}
