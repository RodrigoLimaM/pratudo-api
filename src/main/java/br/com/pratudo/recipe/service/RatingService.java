package br.com.pratudo.recipe.service;

import br.com.pratudo.commons.gamification.GamificationContext;
import br.com.pratudo.commons.gamification.GamificationHandlerByCriteriaFactory;
import br.com.pratudo.commons.utils.SecurityUtils;
import br.com.pratudo.config.exception.ResourceNotFoundException;
import br.com.pratudo.config.exception.UserNotAllowedException;
import br.com.pratudo.recipe.model.GamificationData;
import br.com.pratudo.recipe.model.Rating;
import br.com.pratudo.recipe.model.Recipe;
import br.com.pratudo.recipe.model.dto.RatingDTO;
import br.com.pratudo.recipe.repository.RecipeRepository;
import br.com.pratudo.recipe.repository.RecipeTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeTemplateRepository recipeTemplateRepository;

    @Autowired
    SecurityUtils securityUtils;

    @Autowired
    GamificationHandlerByCriteriaFactory gamificationHandlerByCriteriaFactory;

    public List<Rating> createRating(String recipeId, RatingDTO ratingDTO) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(ResourceNotFoundException::new);

        String current_id = securityUtils.getCurrent_Id();

        List<Rating> ratings = recipe.getRatings();

        if (!IsUserAllowedToRate(recipe, current_id))
            throw new UserNotAllowedException();

        ratings.add(Rating.builder()
                        .owner(current_id)
                        .rating(ratingDTO.getRate())
                .build());

        recipe.setPreparations(ratings.size());

        return recipeTemplateRepository.updateRecipe(recipe)
                .getRatings();
    }

    boolean IsUserAllowedToRate(Recipe recipe, String current_id) {
        return !isRequesterSameAsOwner(recipe, current_id) && !hasAlreadyRated(recipe, current_id);
    }

    private boolean isRequesterSameAsOwner(Recipe recipe, String current_id) {
        return current_id.equals(recipe.getOwner().getId());
    }

    private boolean hasAlreadyRated(Recipe recipe, String current_id) {
        return recipe.getRatings().stream().anyMatch(rating -> rating.getOwner().equals(current_id));
    }

    public GamificationData handleCreateRateGamification() {
        return gamificationHandlerByCriteriaFactory.getUserIdByTypeInstance(GamificationContext.CREATE_RATING)
                .handleGamification();
    }
}
