package br.com.pratudo.user.controller;

import br.com.pratudo.user.model.User;
import br.com.pratudo.user.model.dto.UserDTO;
import br.com.pratudo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{_id}")
    public ResponseEntity<User> getUserBy_Id(@PathVariable final String _id) {
        return userService.getUserBy_Id(_id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid final UserDTO userDTO) throws URISyntaxException {
        User user = userService.createUser(userDTO);
        return ResponseEntity
                .created(new URI("/user/" +user.get_id()))
                .body(user);
    }
}
