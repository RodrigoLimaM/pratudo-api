package br.com.pratudo.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Hit {

    private String _id;

    private BigDecimal _score;

    private _Source _source;
}
