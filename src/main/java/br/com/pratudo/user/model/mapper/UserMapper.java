package br.com.pratudo.user.model.mapper;

import br.com.pratudo.user.model.Badges;
import br.com.pratudo.user.model.Experience;
import br.com.pratudo.user.model.Performance;
import br.com.pratudo.user.model.User;
import br.com.pratudo.user.model.elasticsearch.ElasticsearchSingleUser;
import br.com.pratudo.user.model.elasticsearch.ElasticsearchUser;
import br.com.pratudo.user.model.elasticsearch.UserHit;
import br.com.pratudo.user.model.elasticsearch.UserSource;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User convertElasticsearchUserToUser(ElasticsearchUser elasticsearchUser) {
        UserHit userHit = elasticsearchUser.getUserHits().getUserHits().get(0);
        UserSource source = userHit.getUserSource();
        Performance performance = source.getPerformance();
        Badges badges = performance.getBadges();
        Experience experience = performance.getExperience();

        return buildUser(source, performance, badges, experience, userHit.get_id());
    }

    public User convertElasticsearchSingleUserToUser(ElasticsearchSingleUser elasticsearchSingleUser) {
        UserSource source = elasticsearchSingleUser.getUserSource();
        Performance performance = source.getPerformance();
        Badges badges = performance.getBadges();
        Experience experience = performance.getExperience();

        return buildUser(source, performance, badges, experience, elasticsearchSingleUser.get_id());
    }

    private User buildUser(UserSource source, Performance performance, Badges badges, Experience experience, String id) {
        return User.builder()
                ._id(id)
                .email(source.getEmail())
                .password(source.getPassword())
                .name(source.getName())
                .performance(Performance.builder()
                        .level(performance.getLevel())
                        .experience(Experience.builder()
                                .current(experience.getCurrent())
                                .from(experience.getFrom())
                                .toNextLevel(experience.getToNextLevel())
                                .build())
                        .title(performance.getTitle())
                        .badges(Badges.builder()
                                .owned(badges.getOwned())
                                .count(badges.getCount())
                                .possibleBadges(badges.getPossibleBadges())
                                .build())
                        .build())
                .build();
    }
}
