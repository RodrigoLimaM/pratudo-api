package br.com.pratudo.recipe.controller;

import br.com.pratudo.recipe.model.Comment;
import br.com.pratudo.recipe.model.dto.ContentDTO;
import br.com.pratudo.recipe.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comment")
public class CommentController {

    public static final String URI_PATH_SEPARATOR = "/";

    @Autowired
    CommentService commentService;

    @PostMapping("/{recipeId}")
    public ResponseEntity<List<Comment>> createComment(@PathVariable final String recipeId, @Valid @RequestBody final ContentDTO contentDTO) throws URISyntaxException {
        String newCommentId = UUID.randomUUID().toString();
        final List<Comment> comments = commentService.createComment(recipeId, contentDTO, newCommentId);

        return ResponseEntity
                .created(new URI("/comment/" +recipeId +URI_PATH_SEPARATOR +newCommentId))
                .body(comments);
    }

    @PostMapping("/{recipeId}/{commentId}")
    public ResponseEntity<Comment> createReply(@PathVariable final String recipeId, @PathVariable final String commentId, @Valid @RequestBody final ContentDTO contentDTO) throws URISyntaxException {
        String newReplyId = UUID.randomUUID().toString();
        final Comment comment = commentService.createReply(recipeId, commentId, contentDTO, newReplyId);

        return ResponseEntity
                .created(new URI("/comment/" +recipeId +URI_PATH_SEPARATOR +commentId +URI_PATH_SEPARATOR +newReplyId))
                .body(comment);    }

}
