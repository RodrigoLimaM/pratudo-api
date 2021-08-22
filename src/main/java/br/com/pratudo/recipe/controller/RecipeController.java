package br.com.pratudo.recipe.controller;

import br.com.pratudo.recipe.model.dto.RecipeDTO;
import br.com.pratudo.recipe.model.elasticsearch.ElasticsearchSingleRecipe;
import br.com.pratudo.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @PostMapping
    public ResponseEntity<ElasticsearchSingleRecipe> createRecipe(@RequestBody final RecipeDTO recipeDTO) throws URISyntaxException {
        final ElasticsearchSingleRecipe elasticsearchSingleRecipe = recipeService.createRecipe(recipeDTO);
        return ResponseEntity
                .created(new URI("/recipe/" +elasticsearchSingleRecipe.get_id()))
                .body(elasticsearchSingleRecipe);
    }

}
