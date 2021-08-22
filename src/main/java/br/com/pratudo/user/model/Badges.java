package br.com.pratudo.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Badges {

    @Field(name = "owned")
    private List<String> owned;

    @Field(name = "count")
    private Long count;

    @Field(name = "possibleBadges")
    private Long possibleBadges;
}
