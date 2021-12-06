package br.com.pratudo.recipe.controller;

import br.com.pratudo.recipe.model.GamificationData;
import br.com.pratudo.recipe.model.dto.RatingAndCommentDTO;
import br.com.pratudo.recipe.model.dto.RatingDTO;
import br.com.pratudo.recipe.service.RatingService;
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

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    RatingService ratingService;

    @PostMapping("/{recipeId}")
    public ResponseEntity<GamificationData> createRate(@PathVariable final String recipeId, @RequestBody @Valid final RatingDTO ratingDTO) throws URISyntaxException {
        ratingService.createRate(recipeId, ratingDTO);

        return ResponseEntity
                .created(new URI("/rating/" +recipeId))
                .body(ratingService.handleCreateRateGamification());
    }

    @PostMapping("/rateAndComment/{recipeId}")
    public ResponseEntity<GamificationData> createRateAndComment(@PathVariable final String recipeId, @RequestBody @Valid final RatingAndCommentDTO ratingAndCommentDTO) throws URISyntaxException {
        ratingService.createRateAndComment(recipeId, ratingAndCommentDTO);

        return ResponseEntity
                .created(new URI("/rateAndComment/" +recipeId))
                .body(ratingService.handleCreateRateAndCommentGamification());
    }
}
