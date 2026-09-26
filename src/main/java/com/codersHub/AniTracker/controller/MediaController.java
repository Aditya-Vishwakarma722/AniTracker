package com.codersHub.AniTracker.controller;

import com.codersHub.AniTracker.entity.Media;
import com.codersHub.AniTracker.service.MediaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping
    public Media createMedia(@Valid @RequestBody Media media){
        return mediaService.SaveMedia(media);
    }

    @GetMapping
    public List<Media> getMedia(){
        return mediaService.GetAllMedia();
    }

    @GetMapping("/{id}")
    public Optional<Media> getMediaById(@PathVariable String id){
        return mediaService.getMediaById(id);
    }

    @PutMapping("/{id}")
    public Media updateMedia(@RequestBody Media media, @PathVariable String id){
        return mediaService.updateMedia(id,media);
    }

    @DeleteMapping("/{id}")
    public void deleteMediaById(@PathVariable String id){
        mediaService.deleteMedia(id);
    }
}
