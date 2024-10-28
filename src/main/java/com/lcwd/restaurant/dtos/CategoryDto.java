package com.lcwd.restaurant.dtos;

import ch.qos.logback.core.boolex.EvaluationException;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class CategoryDto {

    private String categoryId;
    @NotBlank(message = "title can not null")
//    @Max(value = 16, message = "write less than 16 title name ")
    private String title ;
    @NotBlank(message = "description is required ")
    private String description ;
//    @NotBlank(message = "cover images required ")

    private String coverImage ;
}
