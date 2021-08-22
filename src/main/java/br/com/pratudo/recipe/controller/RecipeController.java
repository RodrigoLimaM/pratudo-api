package br.com.pratudo.recipe.controller;

import br.com.pratudo.recipe.model.Recipe;
import br.com.pratudo.recipe.model.dto.RecipeDTO;
import br.com.pratudo.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody final RecipeDTO recipeDTO) throws URISyntaxException {
        final Recipe recipe = recipeService.createRecipe(recipeDTO);
        return ResponseEntity
                .created(new URI("/recipe/" +recipe.get_id()))
                .body(recipe);
    }

    @GetMapping("/latest")
    public ResponseEntity<Page<Recipe>> getRecipesOrderByCreationDateDesc(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity
                .ok(recipeService.getRecipesOrderByCreationDateDesc(pageable));
    }

    @GetMapping("/ingredients")
    public ResponseEntity<Page<Recipe>> getRecipesByIngredients(@RequestParam final List<String> ingredients, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity
                .ok(recipeService.getRecipesByIngredients(ingredients, pageable));
    }

    @GetMapping("/name")
    public ResponseEntity<Page<Recipe>> getRecipesByName(@RequestParam final String name, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity
                .ok(recipeService.getRecipesByName(name, pageable));
    }

}
