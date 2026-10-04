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
        Media media = new Media();

        media.setName(mediaRequest.getName());
        media.setDescription(mediaRequest.getDescription());
        media.setType(mediaRequest.getType());
        media.setStatus(mediaRequest.getStatus());
        media.setRating(mediaRequest.getRating());

        Media savedMedia = mediaService.SaveMedia(media);

        MediaResponse response = new MediaResponse();

        response.setId(savedMedia.getId());
        response.setName(savedMedia.getName());
        response.setDescription(savedMedia.getDescription());
        response.setType(savedMedia.getType());
        response.setStatus(savedMedia.getStatus());
        response.setRating(savedMedia.getRating());

        return response;
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
    public Media updateMedia(@Valid @RequestBody Media media, @PathVariable String id){
        return mediaService.updateMedia(id,media);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMediaById(@PathVariable String id){
        mediaService.deleteMedia(id);
        return ResponseEntity.noContent().build();
    }

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
}
