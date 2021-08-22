package br.com.pratudo.recipe.model.mapper;

import br.com.pratudo.recipe.model.Recipe;
import br.com.pratudo.recipe.model.dto.RecipeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper {

    @Autowired
    ModelMapper modelMapper;

    public Recipe convertRecipeDTOToRecipe(RecipeDTO recipeDTO) {
        return modelMapper.map(recipeDTO, Recipe.class);
    }

}
