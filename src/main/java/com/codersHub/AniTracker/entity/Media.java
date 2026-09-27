package com.codersHub.AniTracker.entity;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Media {
    @Id
    private String id;

    @NotBlank(message = "Name is Required!")
    private String name;

    private String description;

    @NotNull(message = "Media Type should not be Null!")
    private MediaType type;

    @NotNull(message = "Media Status should not be Null!")
    private MediaStatus status;
    
    @DecimalMin(message = "Cannot be Smaller than 0!", value = "0.0")
    @DecimalMax(message = "Cannot be greater than 10!", value = "10.0")
    private Double rating;
}
