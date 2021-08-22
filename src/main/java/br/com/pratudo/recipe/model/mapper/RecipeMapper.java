package br.com.pratudo.recipe.model.mapper;

import br.com.pratudo.recipe.model.Recipe;
import br.com.pratudo.recipe.model.elasticsearch.ElasticsearchRecipe;
import br.com.pratudo.recipe.model.elasticsearch.RecipeHit;
import br.com.pratudo.recipe.model.elasticsearch.RecipeSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeMapper {

    public List<Recipe> convertElasticsearchRecipeToRecipe(final ElasticsearchRecipe elasticsearchRecipe) {

        return elasticsearchRecipe.getRecipeHits()
                .getRecipeHits()
                .stream()
                .map(this::convertElasticsearchRecipeToRecipe)
                .collect(Collectors.toList());
    }

    private Recipe convertElasticsearchRecipeToRecipe(final RecipeHit recipeHit) {
        final RecipeSource recipeSource = recipeHit.getRecipeSource();
        return Recipe.builder()
                ._id(recipeHit.get_id())
                .name(recipeSource.getName())
                .images(recipeSource.getImages())
                .owner(recipeSource.getOwner())
                .difficulty(recipeSource.getDifficulty())
                .creationDate(recipeSource.getCreationDate())
                .chefTips(recipeSource.getChefTips())
                .rate(recipeSource.getRate())
                .ratings(recipeSource.getRatings())
                .ingredients(recipeSource.getIngredients())
                .methodOfPreparation(recipeSource.getMethodOfPreparation())
                .comments(recipeSource.getComments())
                .tags(recipeSource.getTags())
                .build();
    }

}
