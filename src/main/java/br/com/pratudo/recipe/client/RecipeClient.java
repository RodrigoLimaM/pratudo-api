package br.com.pratudo.recipe.client;

import br.com.pratudo.commons.search.SearchParams;
import br.com.pratudo.config.security.FeignConfig;
import br.com.pratudo.recipe.model.dto.RecipeDTO;
import br.com.pratudo.recipe.model.elasticsearch.ElasticsearchRecipe;
import br.com.pratudo.recipe.model.elasticsearch.ElasticsearchSingleRecipe;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "RecipeClient", url = "${elasticsearch.url}", configuration = FeignConfig.class, decode404 = true)
public interface RecipeClient {

    String INDEX = "/recipes";

    @PostMapping(INDEX + "/_doc")
    ElasticsearchSingleRecipe createUser(@RequestBody RecipeDTO recipeDTO);

    @PostMapping(INDEX + "/_search")
    ElasticsearchRecipe getRecipesByIngredients(@RequestBody SearchParams searchParams);

    @PostMapping(INDEX + "/_search")
    ElasticsearchRecipe getRecipesByName(@RequestBody SearchParams searchParams);

    @PostMapping(INDEX + "/_search")
    ElasticsearchRecipe getRecipes();
}
