package br.com.pratudo.user.service;

import br.com.pratudo.config.exception.UserAlreadyExistsException;
import br.com.pratudo.user.model.Badges;
import br.com.pratudo.user.model.Experience;
import br.com.pratudo.user.model.Performance;
import br.com.pratudo.user.model.User;
import br.com.pratudo.user.model.dto.UserDTO;
import br.com.pratudo.user.model.enums.Title;
import br.com.pratudo.user.model.mapper.UserMapper;
import br.com.pratudo.user.repository.UserRepository;
import br.com.pratudo.utils.SecurityUtils;
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

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public Optional<User> getUserBy_Id(final String _id) {
        return userRepository.findById(_id);
    }

    public User createUser(final UserDTO userDTO) {
        String email = userDTO.getEmail();

        final User user = userRepository.findUserByEmail(email);

        if(user != null) {
            throw new UserAlreadyExistsException();
        }

        userDTO.setPerformance(buildInitialPerformance());
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

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

    public Performance getCurrentUserPerformance() {
        return userRepository.findById(securityUtils.getCurrent_Id()).map(User::getPerformance).orElse(null);
    }
}
