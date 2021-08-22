package br.com.pratudo.user.model.elasticsearch;

import br.com.pratudo.user.model.Performance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSource {

    private String email;

    private String password;

    private String name;

    private Performance performance;

}