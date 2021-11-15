package br.com.pratudo.user.model.mapper;

import br.com.pratudo.user.model.SummarizedUser;
import br.com.pratudo.user.model.User;
import br.com.pratudo.user.model.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    ModelMapper modelMapper;

    public User convertUserDTOToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public SummarizedUser convertUserToSummarizedUser(User user) {
        return SummarizedUser.builder()
                .name(user.getName())
                .level(user.getPerformance().getLevel())
                .title(user.getPerformance().getTitle())
                .build();
    }
}
