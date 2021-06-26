package br.com.pratudo.user.service;

import br.com.pratudo.user.client.UserClient;
import br.com.pratudo.user.model.User;
import br.com.pratudo.user.model.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserClient userClient;

    @Autowired
    UserMapper userMapper;

    public Optional<User> getUserBy_Id(String _id) {
        return userClient.getUserBy_Id(_id)
                .map(userMapper::convertElasticsearchSingleUserToUser);
    }
}
