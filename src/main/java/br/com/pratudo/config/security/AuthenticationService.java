package br.com.pratudo.config.security;

import br.com.pratudo.commons.search.SearchParamsFactory;
import br.com.pratudo.user.client.UserClient;
import br.com.pratudo.user.model.ElasticsearchUser;
import br.com.pratudo.user.model.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    UserClient userClient;

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ElasticsearchUser elasticsearchUser = userClient.getUserByEmail(SearchParamsFactory.buildGetUserByEmailParams(email));

        if(!isUserExistent(elasticsearchUser)) {
            throw new UsernameNotFoundException("User not found");
        }

        return userMapper.convertElasticsearchUserToUser(elasticsearchUser);
    }

    private boolean isUserExistent(ElasticsearchUser elasticsearchUser) {
        return !elasticsearchUser.getHits().getHits().isEmpty();
    }
}
