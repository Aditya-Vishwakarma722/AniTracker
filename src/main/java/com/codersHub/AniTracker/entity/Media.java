package com.codersHub.AniTracker.entity;

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

    private String name;
    private String description;
    private MediaType type;
    private MediaStatus status;
    private double rating;
}
