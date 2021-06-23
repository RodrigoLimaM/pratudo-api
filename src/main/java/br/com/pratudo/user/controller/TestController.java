package br.com.pratudo.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController {

    @GetMapping("/teste")
    public ResponseEntity<String> getTestMessage() {
        return ResponseEntity.ok("Testado :)");
    }
}
