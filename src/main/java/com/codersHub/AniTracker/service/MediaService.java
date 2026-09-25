package com.codersHub.AniTracker.service;

import com.codersHub.AniTracker.entity.Media;
import com.codersHub.AniTracker.repository.MediaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    //SAVE MEDIA
    public Media SaveMedia(Media media){
        return mediaRepository.save(media);
    }

    //GET ALL MEDIA
    public List<Media> GetAllMedia() { return mediaRepository.findAll(); }

    //GET MEDIA BY ID
    public Optional<Media> getMediaById(String id){ return mediaRepository.findById(id); }
}
