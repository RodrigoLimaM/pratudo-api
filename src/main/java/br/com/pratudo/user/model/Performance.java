package br.com.pratudo.user.model;

import br.com.pratudo.user.model.enums.Title;
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

    private Title title;

    private Badges badges;
}
