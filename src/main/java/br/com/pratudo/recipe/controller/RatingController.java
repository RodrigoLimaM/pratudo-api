package br.com.pratudo.recipe.controller;

import br.com.pratudo.config.properties.ApplicationProperties;
import br.com.pratudo.recipe.model.GamificationAlert;
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

    @Autowired
    ApplicationProperties applicationProperties;

    @PostMapping("/{recipeId}")
    public ResponseEntity<GamificationAlert> createRate(@PathVariable final String recipeId, @RequestBody @Valid final RatingDTO ratingDTO) throws URISyntaxException {
        ratingService.createRating(recipeId, ratingDTO);

        return ResponseEntity
                .created(new URI("/rating/" +recipeId))
                .body(applicationProperties.buildCreateRatingGamificationAlert());
    }
}
