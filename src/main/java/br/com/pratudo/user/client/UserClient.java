package br.com.pratudo.user.client;

import br.com.pratudo.commons.search.SearchParams;
import br.com.pratudo.config.security.FeignConfig;
import br.com.pratudo.user.model.dto.UserDTO;
import br.com.pratudo.user.model.elasticsearch.ElasticsearchSingleUser;
import br.com.pratudo.user.model.elasticsearch.ElasticsearchUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "UserClient", url = "${elasticsearch.url}", configuration = FeignConfig.class, decode404 = true)
public interface UserClient {

    String INDEX = "/users";

    @GetMapping(INDEX + "/_search")
    ElasticsearchUser getUserByEmail(@RequestBody SearchParams searchParams);

    @GetMapping(INDEX + "/_doc/{_id}")
    Optional<ElasticsearchSingleUser> getUserBy_Id(@PathVariable String _id);

    @PostMapping(INDEX + "/_doc")
    ElasticsearchSingleUser createUser(@RequestBody UserDTO userDTO);
}
