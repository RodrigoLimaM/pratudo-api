package br.com.pratudo.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ElasticsearchSingleUser {

    private String _id;

    private _Source _source;
}
