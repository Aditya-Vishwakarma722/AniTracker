package com.codersHub.AniTracker.service;

import com.codersHub.AniTracker.entity.Media;
import com.codersHub.AniTracker.repository.MediaRepository;
import org.springframework.stereotype.Service;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public Media SaveMedia(Media media){
        return mediaRepository.save(media);
    }
}
