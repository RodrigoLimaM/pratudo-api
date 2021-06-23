package br.com.pratudo.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Experience {

    private Long current;

    private Long from;

    private Long toNextLevel;
}
