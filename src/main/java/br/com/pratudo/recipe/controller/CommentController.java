package br.com.pratudo.recipe.controller;

import br.com.pratudo.recipe.model.Comment;
import br.com.pratudo.recipe.model.dto.CommentDTO;
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

    @Autowired
    CommentService commentService;

    @PostMapping("/{recipeId}")
    public ResponseEntity<List<Comment>> createComment(@PathVariable final String recipeId, @Valid @RequestBody final CommentDTO commentDTO) throws URISyntaxException {
        String newCommentId = UUID.randomUUID().toString();
        final List<Comment> comments = commentService.createComment(recipeId, commentDTO, newCommentId);
        return ResponseEntity
                .created(new URI("/comment/" +recipeId +"/" +newCommentId))
                .body(comments);
    }

}
