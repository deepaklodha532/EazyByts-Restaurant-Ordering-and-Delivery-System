package com.lcwd.restaurant.dtos;

import ch.qos.logback.core.boolex.EvaluationException;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDto {

    private String categoryId;
    @NotBlank
    @Min(value = 4, message = "Title must be of minimum 4 character ")
    private String title ;
    @NotBlank(message = "description is required ")
    private String description ;
//    @NotBlank(message = "cover images required ")
    private String coverImage ;
}
