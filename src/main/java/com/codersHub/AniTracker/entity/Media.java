package com.codersHub.AniTracker.entity;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String name;

    private String description;
    private MediaType type;
    private MediaStatus status;
    private double rating;
}
