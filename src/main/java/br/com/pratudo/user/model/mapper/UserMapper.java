package br.com.pratudo.user.model.mapper;

import br.com.pratudo.user.model.Badges;
import br.com.pratudo.user.model.ElasticsearchSingleUser;
import br.com.pratudo.user.model.ElasticsearchUser;
import br.com.pratudo.user.model.Experience;
import br.com.pratudo.user.model.Hit;
import br.com.pratudo.user.model.Performance;
import br.com.pratudo.user.model.User;
import br.com.pratudo.user.model._Source;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User convertElasticsearchUserToUser(ElasticsearchUser elasticsearchUser) {
        Hit hit = elasticsearchUser.getHits().getHits().get(0);
        _Source source = hit.get_source();
        Performance performance = source.getPerformance();
        Badges badges = performance.getBadges();
        Experience experience = performance.getExperience();

        return buildUser(source, performance, badges, experience, hit.get_id());
    }

    public User convertElasticsearchSingleUserToUser(ElasticsearchSingleUser elasticsearchSingleUser) {
        _Source source = elasticsearchSingleUser.get_source();
        Performance performance = source.getPerformance();
        Badges badges = performance.getBadges();
        Experience experience = performance.getExperience();

        return buildUser(source, performance, badges, experience, elasticsearchSingleUser.get_id());
    }

    private User buildUser(_Source source, Performance performance, Badges badges, Experience experience, String id) {
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
                        .build())
                .title(performance.getTitle())
                .badges(Badges.builder()
                        .owned(badges.getOwned())
                        .count(badges.getCount())
                        .possibleBadges(badges.getPossibleBadges())
                        .build())
                .build();
    }
}
