package br.com.pratudo.recipe.controller;

import br.com.pratudo.recipe.model.Recipe;
import br.com.pratudo.recipe.model.SummarizedRecipe;
import br.com.pratudo.recipe.model.dto.RecipeDTO;
import br.com.pratudo.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@Valid @RequestBody final RecipeDTO recipeDTO) throws URISyntaxException {
        final Recipe recipe = recipeService.createRecipe(recipeDTO);
        return ResponseEntity
                .created(new URI("/recipe/" +recipe.get_id()))
                .body(recipe);
    }

    @GetMapping("/latest")
    public ResponseEntity<Page<SummarizedRecipe>> getSummarizedRecipesOrderByCreationDateDesc(Pageable pageable) {
        return ResponseEntity
                .ok(recipeService.getSummarizedRecipesOrderByCreationDateDesc(pageable));
    }

    @GetMapping("/ingredients")
    public ResponseEntity<Page<SummarizedRecipe>> getRecipesByIngredients(@RequestParam(defaultValue = "") final List<String> ingredients,
                                                                Pageable pageable) {
        return ResponseEntity
                .ok(recipeService.getRecipesByIngredients(ingredients, pageable));
    }

    @GetMapping("/name")
    public ResponseEntity<Page<SummarizedRecipe>> getRecipesByName(@RequestParam(defaultValue = "") final String name,
                                                         Pageable pageable) {
        return ResponseEntity
                .ok(recipeService.getRecipesByName(name, pageable));
    }

    @GetMapping("/tag")
    public ResponseEntity<Page<SummarizedRecipe>> getRecipesByTag(@RequestParam(defaultValue = "") final List<String> tags,
                                                         Pageable pageable) {
        return ResponseEntity
                .ok(recipeService.getRecipesByTag(tags, pageable));
    }

    @GetMapping("/{_id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable final String _id) {
        return recipeService.getRecipeById(_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
