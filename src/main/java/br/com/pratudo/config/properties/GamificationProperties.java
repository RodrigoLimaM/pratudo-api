package br.com.pratudo.config.properties;

import br.com.pratudo.recipe.model.GamificationData;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gamification")
@Setter
@Getter
public class GamificationProperties {

    private Long experienceToLevelUp;

    private GamificationData createRecipe;

    private GamificationData createRating;

    private GamificationData createComment;

}
