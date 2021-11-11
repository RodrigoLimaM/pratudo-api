package br.com.pratudo.recipe.controller;

import br.com.pratudo.recipe.model.Recipe;
import br.com.pratudo.recipe.model.SummarizedRecipe;
import br.com.pratudo.recipe.model.UnitOfMeasureValues;
import br.com.pratudo.recipe.model.dto.RecipeDTO;
import br.com.pratudo.recipe.model.enums.Category;
import br.com.pratudo.recipe.model.enums.Criteria;
import br.com.pratudo.recipe.model.enums.UnitOfMeasure;
import br.com.pratudo.recipe.service.RecipeService;
import br.com.pratudo.recipe.model.KeyValue;
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

    @GetMapping
    public ResponseEntity<Page<SummarizedRecipe>> getRecipesByCriteria(Pageable pageable,
                                                                       @RequestParam Criteria criteria,
                                                                       @RequestParam(required = false, defaultValue = "") List<String> ingredients,
                                                                       @RequestParam(required = false, defaultValue = "") String name,
                                                                       @RequestParam(required = false, defaultValue = "") List<Category> categories) {
        return ResponseEntity
                .ok(recipeService.getRecipesByCriteria(pageable, criteria, ingredients, name, categories));
    }

    @GetMapping("/criterias")
    public ResponseEntity<List<KeyValue>> getCriterias() {
        List<KeyValue> keyValues = new ArrayList<>();

        Arrays.stream(Criteria.values()).filter(Criteria::isTab)
                .forEach(criteria -> keyValues.add(KeyValue.builder()
                        .key(criteria.name())
                        .value(criteria.getDescription())
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
