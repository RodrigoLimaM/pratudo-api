package br.com.pratudo.config.properties;

import br.com.pratudo.recipe.model.GamificationAlert;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gamification")
@Setter
public class ApplicationProperties {

    private GamificationAlert createRecipe;

    private GamificationAlert createRating;

    private GamificationAlert createComment;

    public GamificationAlert buildCreateRecipeGamificationAlert() {
        return GamificationAlert
                .builder()
                .gainedExperience(this.createRecipe.getGainedExperience())
                .reason(this.createRecipe.getReason())
                .build();
    }

    public GamificationAlert buildCreateRatingGamificationAlert() {
        return GamificationAlert
                .builder()
                .gainedExperience(this.createRating.getGainedExperience())
                .reason(this.createRating.getReason())
                .build();
    }

    public GamificationAlert buildCreateCommentGamificationAlert() {
        return GamificationAlert
                .builder()
                .gainedExperience(this.createComment.getGainedExperience())
                .reason(this.createComment.getReason())
                .build();
    }
}
