package br.com.pratudo.recipe.service;

import br.com.pratudo.commons.gamification.GamificationContext;
import br.com.pratudo.commons.gamification.GamificationHandlerByCriteriaFactory;
import br.com.pratudo.commons.utils.SecurityUtils;
import br.com.pratudo.config.exception.ResourceNotFoundException;
import br.com.pratudo.recipe.model.Comment;
import br.com.pratudo.recipe.model.GamificationData;
import br.com.pratudo.recipe.model.Recipe;
import br.com.pratudo.recipe.model.Reply;
import br.com.pratudo.recipe.model.dto.ContentDTO;
import br.com.pratudo.recipe.repository.RecipeRepository;
import br.com.pratudo.recipe.repository.RecipeTemplateRepository;
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

    @Autowired
    GamificationHandlerByCriteriaFactory gamificationHandlerByCriteriaFactory;

    public List<Comment> createComment(String recipeId, final ContentDTO contentDTO, String newCommentId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(ResourceNotFoundException::new);

        List<Comment> comments = recipe.getComments();

        comments.add(buildComment(contentDTO, newCommentId));

        return recipeTemplateRepository.updateRecipe(recipe)
                .getComments();
    }

    private Comment buildComment(ContentDTO contentDTO, String newCommentId) {
        return Comment.builder()
                .id(newCommentId)
                .owner(securityUtils.getCurrentName())
                .content(contentDTO.getContent())
                .creationDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .replies(Collections.emptyList())
                .build();
    }

    public Comment createReply(String recipeId, String commentId, ContentDTO contentDTO, String newReplyId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(ResourceNotFoundException::new);

        List<Reply> replies = recipe.getComments()
                .stream()
                .filter(comm -> comm.getId().equals(commentId))
                .findFirst()
                .map(Comment::getReplies)
                .orElse(null);

        replies.add(buildReply(contentDTO, newReplyId));

        return recipeTemplateRepository.updateRecipe(recipe)
                .getComments()
                .stream()
                .filter(comm -> comm.getId().equals(commentId))
                .findFirst()
                .orElse(null);
    }

    private Reply buildReply(ContentDTO contentDTO, String newReplyId) {
        return Reply.builder()
                        .id(newReplyId)
                        .owner(securityUtils.getCurrentName())
                        .content(contentDTO.getContent())
                        .creationDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();
    }

    public GamificationData handleCreateCommentGamification() {
        return gamificationHandlerByCriteriaFactory.getUserIdByTypeInstance(GamificationContext.CREATE_COMMENT)
                .handleGamification();
    }

    public List<Comment> getRecipeComments(String recipeId) {
        return recipeRepository.findById(recipeId)
                .map(Recipe::getComments)
                .orElse(Collections.emptyList());
    }
}
