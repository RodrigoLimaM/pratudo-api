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

    @Field(name = "title")
    private Title title;

    @JsonProperty
    public String getTitle() {
        if (level < 10)
            return Title.INICIANTE.getDescription();
        else if (level >= 10 && level < 20)
            return Title.CHEF.getDescription();
        else
            return Title.MESTRE_CUCA.getDescription();
    }

    @Field(name = "badges")
    private Badges badges;
}
