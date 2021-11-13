package br.com.pratudo.recipe.service;

import br.com.pratudo.recipe.model.Comment;
import br.com.pratudo.recipe.model.Recipe;
import br.com.pratudo.recipe.model.dto.CommentDTO;
import br.com.pratudo.recipe.repository.RecipeRepository;
import br.com.pratudo.recipe.repository.RecipeTemplateRepository;
import br.com.pratudo.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeTemplateRepository recipeTemplateRepository;

    @Autowired
    SecurityUtils securityUtils;

    public List<Comment> createComment(String recipeId, final CommentDTO commentDTO, String newCommentId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        List<Comment> comments = recipe.getComments();

        comments.add(Comment.builder()
                .id(newCommentId)
                .owner(securityUtils.getCurrent_Id())
                .content(commentDTO.getContent())
                .creationDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .replies(Collections.emptyList())
                .build());

        return recipeTemplateRepository.updateRecipe(recipe).getComments();
    }
}
