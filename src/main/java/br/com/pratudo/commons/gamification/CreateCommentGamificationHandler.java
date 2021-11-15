package br.com.pratudo.commons.gamification;

import br.com.pratudo.config.properties.GamificationProperties;
import br.com.pratudo.recipe.model.GamificationData;
import br.com.pratudo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class CreateCommentGamificationHandler implements GamificationHandler {

    private GamificationProperties gamificationProperties;

    private UserService userService;

    @Autowired
    public CreateCommentGamificationHandler(GamificationProperties gamificationProperties, UserService userService) {
        this.gamificationProperties = gamificationProperties;
        this.userService = userService;
    }

    @Override
    public GamificationData handleGamification() {
        GamificationData gamificationData = gamificationProperties.getCreateComment();

        userService.addExperience(gamificationData.getGainedExperience());

        return GamificationData.of(gamificationData.getGainedExperience(), gamificationData.getReason());
    }
}
