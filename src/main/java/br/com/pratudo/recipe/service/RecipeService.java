package br.com.pratudo.recipe.service;

import br.com.pratudo.commons.search.SearchParamsFactory;
import br.com.pratudo.recipe.client.RecipeClient;
import br.com.pratudo.recipe.model.MethodOfPreparation;
import br.com.pratudo.recipe.model.Owner;
import br.com.pratudo.recipe.model.Recipe;
import br.com.pratudo.recipe.model.Time;
import br.com.pratudo.recipe.model.dto.RecipeDTO;
import br.com.pratudo.recipe.model.elasticsearch.ElasticsearchSingleRecipe;
import br.com.pratudo.recipe.model.mapper.RecipeMapper;
import br.com.pratudo.recipe.repository.RecipeRepository;
import br.com.pratudo.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RecipeService {

    @Autowired
    RecipeClient recipeClient;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeMapper recipeMapper;

    public ElasticsearchSingleRecipe createRecipe(final RecipeDTO recipeDTO) {
        recipeDTO.setOwner(buildInitialOwner());

        recipeDTO.setCreationDate(LocalDate.now());

        recipeDTO.setRate(BigDecimal.ZERO);

        recipeDTO.setRatings(Collections.emptyList());

        final MethodOfPreparation methodOfPreparation = recipeDTO.getMethodOfPreparation();
        methodOfPreparation.setTime(new Time());
        final Time totalMethodOfPreparationTime = methodOfPreparation.getTime();
        totalMethodOfPreparationTime.setTimeUnit(TimeUnit.MINUTES);
        totalMethodOfPreparationTime.setValue(methodOfPreparation.getTotalMinutesInMethodOfPreparation());

        recipeDTO.setComments(Collections.emptyList());

        return recipeClient.createUser(recipeDTO);
    }

    private Owner buildInitialOwner() {
        return Owner.builder()
                ._id(getCurrent_Id())
                .name(getCurrentName())
                .build();
    }

    public String getCurrent_Id() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof User) {
                return ((User) principal).get_id();
            }
        }
        return null;
    }

    public String getCurrentName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof User) {
                return ((User) principal).getName();
            }
        }
        return null;
    }

    public Page<Recipe> getRecipes(final Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    public Page<Recipe> getRecipesByIngredients(final List<String> ingredients, final Pageable pageable) {
        return recipeRepository.findByIngredients(ingredients.toString().replace("[", "").replace("]", ""), pageable);
    }

    public Page<Recipe> getRecipesByName(final String name, final Pageable pageable) {
        return recipeRepository.findByName(name, pageable);
    }

}
