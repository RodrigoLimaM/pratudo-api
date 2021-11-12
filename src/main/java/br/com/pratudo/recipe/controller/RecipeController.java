package br.com.pratudo.recipe.controller;

import br.com.pratudo.recipe.model.KeyValue;
import br.com.pratudo.recipe.model.Recipe;
import br.com.pratudo.recipe.model.SummarizedRecipe;
import br.com.pratudo.recipe.model.SummarizedRecipeWithIngredients;
import br.com.pratudo.recipe.model.UnitOfMeasureValues;
import br.com.pratudo.recipe.model.dto.RecipeDTO;
import br.com.pratudo.recipe.model.enums.Category;
import br.com.pratudo.recipe.model.enums.Difficulty;
import br.com.pratudo.recipe.model.enums.Trend;
import br.com.pratudo.recipe.model.enums.UnitOfMeasure;
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
import java.util.ArrayList;
import java.util.Arrays;
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
    public ResponseEntity<List<KeyValue>> getCriterias() {
        List<KeyValue> keyValues = new ArrayList<>();

        Arrays.stream(Trend.values())
                .forEach(trend -> keyValues.add(KeyValue.builder()
                        .key(trend.name())
                        .value(trend.getDescription())
                        .build())
                );

        return ResponseEntity
                .ok(keyValues);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<KeyValue>> getCategories() {
        List<KeyValue> keyValues = new ArrayList<>();

        Arrays.stream(Category.values())
                .forEach(category -> keyValues.add(KeyValue.builder()
                        .key(category.name())
                        .value(category.getDescription())
                        .build())
                );

        return ResponseEntity
                .ok(keyValues);
    }

    @GetMapping("/units-of-measure")
    public ResponseEntity<List<UnitOfMeasureValues>> getUnitsOfMeasure() {
        List<UnitOfMeasureValues> unitOfMeasureValues = new ArrayList<>();

        Arrays.stream(UnitOfMeasure.values())
                .forEach(unitOfMeasure -> unitOfMeasureValues.add(
                        UnitOfMeasureValues.builder()
                                .key(unitOfMeasure.name())
                                .translate(unitOfMeasure.getTranslation())
                                .abbreviation(unitOfMeasure.getAbbreviation())
                                .build()
                        )
                );

        return ResponseEntity
                .ok(unitOfMeasureValues);
    }

    @GetMapping("/{_id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable final String _id) {
        return recipeService.getRecipeById(_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
