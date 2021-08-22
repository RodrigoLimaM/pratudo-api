package br.com.pratudo.user.repository;

import br.com.pratudo.user.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface UserRepository extends ElasticsearchRepository<User, String> {

    @Override
    Optional<User> findById(String id);

    User findUserByEmail(String email);
}
