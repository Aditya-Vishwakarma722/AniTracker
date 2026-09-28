package com.codersHub.AniTracker.service;

import com.codersHub.AniTracker.entity.Media;
import com.codersHub.AniTracker.exception.ResourceNotFoundException;
import com.codersHub.AniTracker.repository.MediaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Media getMediaById(String id){ return mediaRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Media not found with id: " + id
                    )
            );
    }

    //UPDATE MEDIA BY ID
    public Media updateMedia(String id, Media media){
        Media existingMedia = mediaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Media not found with id: " + id
                        )
                );

        existingMedia.setName(media.getName());
        existingMedia.setDescription(media.getDescription());
        existingMedia.setType(media.getType());
        existingMedia.setStatus(media.getStatus());
        existingMedia.setRating(media.getRating());

        return mediaRepository.save(existingMedia);
    }

    //DELETE MEDIA BY ID
    public void deleteMedia(String id){
        if (!mediaRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Media not found with id: " + id
            );
        }
        mediaRepository.deleteById(id);
    }

}
