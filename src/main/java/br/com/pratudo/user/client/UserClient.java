package br.com.pratudo.user.client;

import br.com.pratudo.commons.search.SearchParams;
import br.com.pratudo.config.security.FeignConfig;
import br.com.pratudo.user.model.ElasticsearchSingleUser;
import br.com.pratudo.user.model.ElasticsearchUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "UserClient", url = "${elasticsearch.url}", configuration = FeignConfig.class, decode404 = true)
public interface UserClient {

    String INDEX = "/users";

    @PostMapping(INDEX + "/_search")
    ElasticsearchUser getUserByEmail(@RequestBody SearchParams searchParams);

    @GetMapping(INDEX + "/_doc/{_id}")
    ElasticsearchSingleUser getUserBy_Id(@PathVariable String _id);
}
