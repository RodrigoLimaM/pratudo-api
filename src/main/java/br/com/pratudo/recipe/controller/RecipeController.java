package br.com.pratudo.recipe.controller;

import br.com.pratudo.config.properties.GamificationProperties;
import br.com.pratudo.recipe.model.CategorieValues;
import br.com.pratudo.recipe.model.GamificationData;
import br.com.pratudo.recipe.model.Recipe;
import br.com.pratudo.recipe.model.SummarizedRecipe;
import br.com.pratudo.recipe.model.SummarizedRecipeWithIngredients;
import br.com.pratudo.recipe.model.UnitOfMeasureValues;
import br.com.pratudo.recipe.model.dto.RecipeDTO;
import br.com.pratudo.recipe.model.enums.Category;
import br.com.pratudo.recipe.model.enums.Difficulty;
import br.com.pratudo.recipe.model.enums.Trend;
import br.com.pratudo.recipe.service.RecipeService;
import br.com.pratudo.user.service.UserService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserService userService;

    @Autowired
    GamificationProperties gamificationProperties;

    @PostMapping
    public ResponseEntity<GamificationData> createRecipe(@Valid @RequestBody final RecipeDTO recipeDTO) throws URISyntaxException {
        final Recipe recipe = recipeService.createRecipe(recipeDTO);
        return ResponseEntity
                .created(new URI("/recipe/" +recipe.get_id()))
                .body(recipeService.handleCreateRecipeGamification());
    }

    @GetMapping("/trend")
    public ResponseEntity<Page<SummarizedRecipe>> getRecipesByTrend(Pageable pageable, @RequestParam(value = "filter") Trend trend) {
        return ResponseEntity
                .ok(recipeService.getRecipesByTrend(pageable, trend));
    }

    @GetMapping
    public ResponseEntity<Page<SummarizedRecipeWithIngredients>> getRecipesByQuery(Pageable pageable,
                                                                                   @RequestParam(required = false) List<Category> categories,
                                                                                   @RequestParam(required = false) List<Difficulty> difficulties,
                                                                                   @RequestParam(required = false) Long serves,
                                                                                   @RequestParam(required = false) String name,
                                                                                   @RequestParam(required = false) List<String> ingredients) {
        return ResponseEntity
                .ok(recipeService.getRecipesByQuery(pageable, categories, difficulties, serves, name, ingredients));
    }

    @GetMapping("/trends")
    public ResponseEntity<List<CategorieValues>> getTrends() {
        List<CategorieValues> categorieValues = new ArrayList<>();

        Arrays.stream(Trend.values())
                .forEach(trend -> categorieValues.add(CategorieValues.builder()
                        .key(trend.name())
                        .value(trend.getDescription())
                        .build())
                );

        return ResponseEntity
                .ok(categorieValues);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategorieValues>> getCategories() {
        return ResponseEntity
                .ok(recipeService.getCategories());
    }

    @GetMapping("/units-of-measure")
    public ResponseEntity<List<UnitOfMeasureValues>> getUnitsOfMeasure() {
        return ResponseEntity
                .ok(recipeService.getUnitsOfMeasure());
    }

    @GetMapping("/{_id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable final String _id) {
        return recipeService.getRecipeById(_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/my-recipes")
    public ResponseEntity<Page<SummarizedRecipe>> getMyRecipes(Pageable pageable) {
        return ResponseEntity
                .ok(recipeService.getMyRecipes(pageable));
    }

}
