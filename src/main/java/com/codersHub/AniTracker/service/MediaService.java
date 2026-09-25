package com.codersHub.AniTracker.service;

import com.codersHub.AniTracker.entity.Media;
import com.codersHub.AniTracker.repository.MediaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public Media SaveMedia(Media media){
        return mediaRepository.save(media);
    }

    public List<Media> GetAllMedia(Media media){
        return mediaRepository.findAll();
    }

    public List<Media> GetAllMedia() {
        return mediaRepository.findAll();
    }
}
