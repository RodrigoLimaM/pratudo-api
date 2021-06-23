package br.com.pratudo.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Performance {

    private Long level;

    private Experience experience;

    private String title;

    private Badges badges;
}
