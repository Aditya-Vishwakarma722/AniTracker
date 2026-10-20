package com.codersHub.AniTracker.mapper;

import com.codersHub.AniTracker.dto.MediaRequest;
import com.codersHub.AniTracker.dto.MediaResponse;
import com.codersHub.AniTracker.entity.Media;

public class MediaMapper {

    public Media toMedia(MediaRequest mediaRequest) {

        Media media = new Media();

        media.setName(mediaRequest.getName());
        media.setDescription(mediaRequest.getDescription());
        media.setType(mediaRequest.getType());
        media.setStatus(mediaRequest.getStatus());
        media.setRating(mediaRequest.getRating());

        return media;
    }

    public MediaResponse toMediaResponse(Media media) {

        MediaResponse response = new MediaResponse();

        response.setId(media.getId());
        response.setName(media.getName());
        response.setDescription(media.getDescription());
        response.setType(media.getType());
        response.setStatus(media.getStatus());
        response.setRating(media.getRating());

        return response;
    }
}