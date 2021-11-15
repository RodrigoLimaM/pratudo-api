package br.com.pratudo.commons.gamification;

import br.com.pratudo.config.properties.GamificationProperties;
import br.com.pratudo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class GamificationHandlerByCriteriaFactory {

    private static final Map<GamificationContext, GamificationHandler> CRITERIA_BY_GAMIFICATION_HANDLER_MAP = new EnumMap<>(GamificationContext.class);

    @Autowired
    public GamificationHandlerByCriteriaFactory(GamificationProperties gamificationProperties,
                                                UserService userService) {
        CRITERIA_BY_GAMIFICATION_HANDLER_MAP.put(GamificationContext.CREATE_RECIPE, new CreateRecipeGamificationHandler(gamificationProperties, userService));
        CRITERIA_BY_GAMIFICATION_HANDLER_MAP.put(GamificationContext.CREATE_COMMENT, new CreateCommentGamificationHandler(gamificationProperties, userService));
        CRITERIA_BY_GAMIFICATION_HANDLER_MAP.put(GamificationContext.CREATE_RATING, new CreateRatingGamificationHandler(gamificationProperties, userService));
    }

    public GamificationHandler getUserIdByTypeInstance(GamificationContext gamificationContext) {
        return CRITERIA_BY_GAMIFICATION_HANDLER_MAP.get(gamificationContext);
    }

}
