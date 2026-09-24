package com.codersHub.AniTracker.controller;

import com.codersHub.AniTracker.entity.Media;
import com.codersHub.AniTracker.service.MediaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping
    public Media createMedia(@RequestBody Media media){
        return mediaService.SaveMedia(media);
    }
}
