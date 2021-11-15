package br.com.pratudo.config.security;

import br.com.pratudo.user.model.User;
import br.com.pratudo.user.model.mapper.UserMapper;
import br.com.pratudo.user.repository.UserRepository;
import br.com.pratudo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userRepository.findUserByEmail(email);

        if (user == null)
            throw new UsernameNotFoundException("User not found");

        return user;
    }

}
