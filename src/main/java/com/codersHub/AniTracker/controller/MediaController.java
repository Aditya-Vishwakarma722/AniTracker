package com.codersHub.AniTracker.controller;

import com.codersHub.AniTracker.dto.MediaRequest;
import com.codersHub.AniTracker.dto.MediaResponse;
import com.codersHub.AniTracker.entity.Media;
import com.codersHub.AniTracker.service.MediaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codersHub.AniTracker.mapper.MediaMapper;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaService mediaService;
    private final MediaMapper mediaMapper = new MediaMapper();

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping
    public MediaResponse createMedia(@Valid @RequestBody MediaRequest mediaRequest){
        Media media = mediaMapper.toMedia(mediaRequest);
        Media savedMedia = mediaService.SaveMedia(media);
        return mediaMapper.toMediaResponse(savedMedia);
    }

    @GetMapping
    public List<MediaResponse> getMedia(){
        List<Media> medialist = mediaService.GetAllMedia();

        return medialist.stream().map(media -> mediaMapper.toMediaResponse(media)).toList();

    }

    @GetMapping("/{id}")
    public MediaResponse getMediaById(@PathVariable String id){
        Media media = mediaService.getMediaById(id);
        return mediaMapper.toMediaResponse(media);
    }

    @PutMapping("/{id}")
    public MediaResponse updateMedia(@Valid @RequestBody MediaRequest mediaRequest, @PathVariable String id){
        Media media = mediaMapper.toMedia(mediaRequest);
        Media updatedMedia = mediaService.updateMedia(id, media);
        return mediaMapper.toMediaResponse(updatedMedia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMediaById(@PathVariable String id){
        mediaService.deleteMedia(id);
        return ResponseEntity.noContent().build();
    }
}
