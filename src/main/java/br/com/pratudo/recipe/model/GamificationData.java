package br.com.pratudo.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GamificationData {

    private Long gainedExperience;

    private String reason;

    public static GamificationData of(Long gainedExperience, String reason) {
        return GamificationData.builder()
                .gainedExperience(gainedExperience)
                .reason(reason)
                .build();
    }
}
