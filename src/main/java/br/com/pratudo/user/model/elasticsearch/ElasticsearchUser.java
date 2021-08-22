package br.com.pratudo.user.model.elasticsearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ElasticsearchUser {

    @JsonProperty("hits")
    private UserHits userHits;
}