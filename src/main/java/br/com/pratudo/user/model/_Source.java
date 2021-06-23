package br.com.pratudo.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class _Source {

    private String email;

    private String password;

    private String name;

    private Performance performance;

}