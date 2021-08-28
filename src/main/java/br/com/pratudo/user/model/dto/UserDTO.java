package br.com.pratudo.user.model.dto;

import br.com.pratudo.user.model.Performance;
import br.com.pratudo.validation.Email;
import br.com.pratudo.validation.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    @NotBlank
    @NotNull
    @Email
    private String email;

    @NotBlank
    @NotNull
    @Password
    private String password;

    @NotBlank
    @NotNull
    private String name;

    private Performance performance;

}
