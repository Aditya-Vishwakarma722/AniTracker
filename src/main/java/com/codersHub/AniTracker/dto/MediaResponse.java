package com.codersHub.AniTracker.dto;

import com.codersHub.AniTracker.entity.MediaStatus;
import com.codersHub.AniTracker.entity.MediaType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaResponse {

    private String id;
    private String name;
    private String description;
    private MediaType type;
    private MediaStatus status;
    private Double rating;
}