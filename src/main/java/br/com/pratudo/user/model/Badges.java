package br.com.pratudo.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Badges {

    private List<String> owned;

    private Long count;

    private Long possibleBadges;
}
