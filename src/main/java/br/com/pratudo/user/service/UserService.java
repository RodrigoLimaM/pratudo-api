package br.com.pratudo.user.service;

import br.com.pratudo.commons.utils.SecurityUtils;
import br.com.pratudo.config.exception.ResourceNotFoundException;
import br.com.pratudo.config.exception.UserAlreadyExistsException;
import br.com.pratudo.config.properties.GamificationProperties;
import br.com.pratudo.user.model.Badges;
import br.com.pratudo.user.model.Experience;
import br.com.pratudo.user.model.Performance;
import br.com.pratudo.user.model.SummarizedUser;
import br.com.pratudo.user.model.User;
import br.com.pratudo.user.model.dto.UserDTO;
import br.com.pratudo.user.model.mapper.UserMapper;
import br.com.pratudo.user.repository.UserRepository;
import br.com.pratudo.user.repository.UserTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SecurityUtils securityUtils;

    @Autowired
    UserTemplateRepository userTemplateRepository;

    @Autowired
    GamificationProperties gamificationProperties;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public User createUser(final UserDTO userDTO) {
        String email = userDTO.getEmail();

        final User user = userRepository.findUserByEmail(email);

        if (user != null) {
            throw new UserAlreadyExistsException();
        }

        userDTO.setPerformance(buildInitialPerformance());
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        System.out.println(userMapper.convertUserDTOToUser(userDTO));

        return userRepository.save(userMapper.convertUserDTOToUser(userDTO));
    }

    public boolean isUserExistent(List<User> users) {
        return users.isEmpty();
    }

    private Performance buildInitialPerformance() {
        return Performance.builder()
                    .level(1L)
                    .experience(Experience.builder()
                            .current(0L)
                            .from(gamificationProperties.getExperienceToLevelUp())
                            .build())
                .badges(Badges.builder()
                        .owned(Collections.emptyList())
                        .possibleBadges(10L)
                        .build())
                .build();
    }

    public Performance getCurrentUserPerformance() {
        return userRepository.findById(securityUtils.getCurrent_Id()).map(User::getPerformance).orElse(null);
    }

    public void addExperience(Long gainedExperience) {
        User user = userRepository.findById(securityUtils.getCurrent_Id())
                .orElseThrow(ResourceNotFoundException::new);

        Performance performance = user.getPerformance();
        Experience experience = performance.getExperience();

        if(gainedExperience >= experience.getToNextLevel()) {
            experience.setCurrent(experience.getCurrent() - gainedExperience);
            performance.setLevel(performance.getLevel() + 1);
        } else {
            experience.setCurrent(experience.getCurrent() + gainedExperience);
        }

        userTemplateRepository.updateUser(user);
    }

    public Optional<SummarizedUser> getCurrentUserSummarizedData() {
        return userRepository.findById(securityUtils.getCurrent_Id())
                .map(userMapper::convertUserToSummarizedUser);
    }
}
