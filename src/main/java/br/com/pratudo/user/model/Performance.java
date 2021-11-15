package br.com.pratudo.user.model;

import br.com.pratudo.user.model.enums.Title;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Performance {

    @Field(name = "level")
    private Long level;

    @Field(name = "experience")
    private Experience experience;

    @JsonProperty
    public String getTitle() {
        if (level < 5)
            return Title.INICIANTE.getDescription();
        else if (level >= 5 && level < 10)
            return Title.COZINHEIRO.getDescription();
        else if (level >= 10 && level < 15)
            return Title.SUBCHEFE.getDescription();
        else if (level >= 15 && level < 20)
            return Title.CHEFE_DE_COZINHA.getDescription();
        else
            return Title.MESTRE_CUCA.getDescription();
    }

    @Field(name = "badges")
    private Badges badges;
}
