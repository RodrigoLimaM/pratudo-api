package br.com.pratudo.user.client;

import br.com.pratudo.commons.search.SearchParams;
import br.com.pratudo.user.model.ElasticsearchSingleUser;
import br.com.pratudo.user.model.ElasticsearchUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "UserClient", url = "${elasticsearch.url}")
public interface UserClient {

    String INDEX = "/pratudo/users";

    @GetMapping(INDEX + "/_search")
    ElasticsearchUser getUserByEmail(@RequestBody SearchParams searchParams);

    @GetMapping(INDEX + "/{_id}")
    ElasticsearchSingleUser getUserBy_Id(@PathVariable String _id);
}
