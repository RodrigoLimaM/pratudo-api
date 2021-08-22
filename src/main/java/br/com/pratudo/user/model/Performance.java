package br.com.pratudo.user.model;

import br.com.pratudo.user.model.enums.Title;
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

    @Field(name = "badges")
    private Badges badges;
}
