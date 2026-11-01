package com.codersHub.AniTracker.dto;

import com.codersHub.AniTracker.entity.MediaStatus;
import com.codersHub.AniTracker.entity.MediaType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Media type is required")
    private MediaType type;

    @NotNull(message = "Media status is required")
    private MediaStatus status;
}
