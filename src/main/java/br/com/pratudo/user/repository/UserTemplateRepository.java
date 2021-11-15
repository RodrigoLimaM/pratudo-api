package br.com.pratudo.user.repository;

import br.com.pratudo.user.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.DocumentOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Repository;

@Repository
public class UserTemplateRepository {

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    DocumentOperations documentOperations;

    @Autowired
    ObjectMapper objectMapper;

    @SneakyThrows
    public User updateUser(User newUser) {
        String _id = newUser.get_id();

        newUser.set_id(null);

        System.out.println("************\n" +objectMapper.writeValueAsString(newUser));

        UpdateQuery updateQuery = UpdateQuery.builder(_id)
                .withDocument(Document.parse(objectMapper.writeValueAsString(newUser)))
                .build();

        documentOperations.update(updateQuery, IndexCoordinates.of("users"));

        return newUser;
    }

}
