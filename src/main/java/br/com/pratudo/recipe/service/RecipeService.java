package br.com.pratudo.recipe.service;

import br.com.pratudo.recipe.model.Owner;
import br.com.pratudo.recipe.model.Recipe;
import br.com.pratudo.recipe.model.SummarizedRecipe;
import br.com.pratudo.recipe.model.dto.RecipeDTO;
import br.com.pratudo.recipe.model.enums.Category;
import br.com.pratudo.recipe.model.enums.Criteria;
import br.com.pratudo.recipe.model.mapper.RecipeMapper;
import br.com.pratudo.recipe.repository.RecipeRepository;
import br.com.pratudo.utils.SecurityUtils;
import br.com.pratudo.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeMapper recipeMapper;

    @Autowired
    SecurityUtils securityUtils;

    @Autowired
    StringUtils stringUtils;

    @Autowired
    AnalyzerService analyzerService;

    @Autowired
    SearcherByCriteriaFactory searcherByCriteriaFactory;

    public Recipe createRecipe(final RecipeDTO recipeDTO) {
        recipeDTO.setOwner(buildInitialOwner());

        recipeDTO.setCreationDate(LocalDateTime.now());

        recipeDTO.setRatings(Collections.emptyList());

        recipeDTO.setComments(Collections.emptyList());

        return recipeRepository.save(recipeMapper.convertRecipeDTOToRecipe(recipeDTO));
    }

    private Owner buildInitialOwner() {
        return Owner.builder()
                ._id(securityUtils.getCurrent_Id())
                .name(securityUtils.getCurrentName())
                .build();
    }

    public Optional<Recipe> getRecipeById(String _id) {
        return recipeRepository.findById(_id);
    }

    public Page<SummarizedRecipe> getRecipesByCriteria(Pageable pageable, Criteria criteria, List<String> ingredients, String name, List<Category> categories) {
        return searcherByCriteriaFactory.getUserIdByTypeInstance(criteria)
                .getRecipesByCriteria(pageable, ingredients, name, categories);
    }
}
