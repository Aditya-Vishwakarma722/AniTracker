package com.codersHub.AniTracker.controller;

import com.codersHub.AniTracker.dto.MediaRequest;
import com.codersHub.AniTracker.dto.MediaResponse;
import com.codersHub.AniTracker.entity.Media;
import com.codersHub.AniTracker.exception.ResourceNotFoundException;
import com.codersHub.AniTracker.service.MediaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping
    public MediaResponse createMedia(@Valid @RequestBody MediaRequest mediaRequest){
        Media media = toMedia(mediaRequest);
        Media savedMedia = mediaService.SaveMedia(media);
        return toMediaResponse(savedMedia);
    }

    @GetMapping
    public List<MediaResponse> getMedia(){
        List<Media> medialist = mediaService.GetAllMedia();

        return medialist.stream().map(media -> toMediaResponse(media)).toList();

    }

    @GetMapping("/{id}")
    public MediaResponse getMediaById(@PathVariable String id){
        Media media = mediaService.getMediaById(id);
        return toMediaResponse(media);
    }

    @PutMapping("/{id}")
    public MediaResponse updateMedia(@Valid @RequestBody MediaRequest mediaRequest, @PathVariable String id){
        Media media = toMedia(mediaRequest);
        Media updatedMedia = mediaService.updateMedia(id, media);
        return toMediaResponse(updatedMedia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMediaById(@PathVariable String id){
        mediaService.deleteMedia(id);
        return ResponseEntity.noContent().build();
    }

    //Request and Response

    private MediaResponse toMediaResponse(Media media) {

        MediaResponse response = new MediaResponse();

        response.setId(media.getId());
        response.setName(media.getName());
        response.setDescription(media.getDescription());
        response.setType(media.getType());
        response.setStatus(media.getStatus());
        response.setRating(media.getRating());

        return response;
    }

    private Media toMedia(MediaRequest mediaRequest){
        Media media = new Media();

        media.setName(mediaRequest.getName());
        media.setDescription(mediaRequest.getDescription());
        media.setType(mediaRequest.getType());
        media.setStatus(mediaRequest.getStatus());
        media.setRating(mediaRequest.getRating());

        return media;
    }
}
