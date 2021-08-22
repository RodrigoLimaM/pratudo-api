package br.com.pratudo.user.service;

import br.com.pratudo.commons.search.SearchParamsFactory;
import br.com.pratudo.config.exception.UserAlreadyExistsException;
import br.com.pratudo.user.client.UserClient;
import br.com.pratudo.user.model.Badges;
import br.com.pratudo.user.model.Experience;
import br.com.pratudo.user.model.Performance;
import br.com.pratudo.user.model.User;
import br.com.pratudo.user.model.dto.UserDTO;
import br.com.pratudo.user.model.elasticsearch.ElasticsearchSingleUser;
import br.com.pratudo.user.model.elasticsearch.ElasticsearchUser;
import br.com.pratudo.user.model.enums.Title;
import br.com.pratudo.user.model.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserClient userClient;

    @Autowired
    UserMapper userMapper;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public Optional<User> getUserBy_Id(String _id) {
        return userClient.getUserBy_Id(_id)
                .map(userMapper::convertElasticsearchSingleUserToUser);
    }

    public ElasticsearchSingleUser createUser(UserDTO userDTO) {
        ElasticsearchUser elasticsearchUser = userClient.getUserByEmail(SearchParamsFactory.buildGetUserByEmailParams(userDTO.getEmail()));

        if(isUserExistent(elasticsearchUser)) {
            throw new UserAlreadyExistsException();
        }

        userDTO.setPerformance(buildInitialPerformance());
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        return userClient.createUser(userDTO);
    }

    public boolean isUserExistent(ElasticsearchUser elasticsearchUser) {
        return !elasticsearchUser.getUserHits().getUserHits().isEmpty();
    }

    private Performance buildInitialPerformance() {
        return Performance.builder()
                    .level(1L)
                    .experience(Experience.builder()
                            .current(0L)
                            .from(1000L)
                            .toNextLevel(1000L)
                            .build())
                .title(Title.INICIANTE)
                .badges(Badges.builder()
                        .owned(Collections.emptyList())
                        .count(0L)
                        .possibleBadges(10L)
                        .build())
                .build();
    }
}
