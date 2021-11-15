package br.com.pratudo.recipe.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RatingDTO {

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "5.00")
    private Double rate;

}
