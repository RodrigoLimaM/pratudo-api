package br.com.pratudo.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Experience {

    @Field(name = "current")
    private Long current;

    @Field(name = "from")
    private Long from;

    @Field(name = "toNextLevel")
    private Long toNextLevel;
}
