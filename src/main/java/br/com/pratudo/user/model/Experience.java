package br.com.pratudo.user.model;

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
public class Experience {

    @Field(name = "current")
    private Long current;

    @Field(name = "from")
    private Long from;

    @JsonProperty
    public Long getToNextLevel() {
        return from - current;
    }

    @JsonProperty
    public Long getPercentage() {
        return Math.round((Double.valueOf(this.current) / Double.valueOf(this.from)) * 100);
    }

}
