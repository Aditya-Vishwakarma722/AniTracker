package com.codersHub.AniTracker.service;
import com.codersHub.AniTracker.entity.Media;
import com.codersHub.AniTracker.exception.ResourceNotFoundException;
import com.codersHub.AniTracker.repository.MediaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.ResourceAccessException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MediaServiceTest {

    @Mock
    private MediaRepository mediaRepository;

    @InjectMocks
    private MediaService mediaService;

    @Test
    void GetAllMediaTest(){
        Media media = new Media();
        media.setName("Attack on Titan");

        List<Media> expectedMedia = List.of(media);

        when(mediaRepository.findAll()).thenReturn(expectedMedia);

        List<Media> actualMedia = mediaService.GetAllMedia();

        assertEquals(expectedMedia,actualMedia);
        verify(mediaRepository).findAll();

    }

    @Test
    void GetNoMediaTest(){
        Media media = new Media();
        List<Media> mediaList = List.of();

        when(mediaRepository.findAll()).thenReturn(mediaList);

        List<Media> actualMedia = mediaService.GetAllMedia();

        assertEquals(mediaList,actualMedia);
        verify(mediaRepository).findAll();
    }

    @Test
    void GetMediaByIdTest(){
        String id = "123";
        Media expectedMedia = new Media();
        expectedMedia.setId(id);
        expectedMedia.setName("Tokyo Ghoul");

        when(mediaRepository.findById(id)).thenReturn(java.util.Optional.of(expectedMedia));

        Media actualMedia = mediaService.getMediaById(id);

        assertEquals(expectedMedia,actualMedia);
        verify(mediaRepository).findById(id);
    }

    @Test
    void GetMediaByIdNotFound(){
        String id = "999";
        when(mediaRepository.findById(id)).thenReturn(java.util.Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> mediaService.getMediaById(id)
        );
        verify(mediaRepository).findById(id);
    }

    @Test
    void SaveMediaTest(){
        Media media = new Media();
        media.setName("One Piece");

        when(mediaRepository.save(media)).thenReturn(media);

        Media savedMedia = mediaService.SaveMedia(media);

        assertEquals(media,savedMedia);
        verify(mediaRepository).save(media);
    }

    @Test
    void UpdateMediaTest(){
        String id = "123";

        Media existingMedia = new Media();
        existingMedia.setId(id);
        existingMedia.setName("One Piece");
        existingMedia.setDescription("Nothing for now");

        Media updatedMedia = new Media();
        updatedMedia.setName("One Piece");
        updatedMedia.setDescription("Updated Description");

        when(mediaRepository.findById(id)).thenReturn(java.util.Optional.of(existingMedia));
        when(mediaRepository.save(existingMedia)).thenReturn(existingMedia);

        Media result = mediaService.updateMedia(id, updatedMedia);

        assertEquals("One Piece", result.getName());
        assertEquals("Updated Description", result.getDescription());

        verify(mediaRepository).findById(id);
        verify(mediaRepository).save(existingMedia);
    }

    @Test
    void UpdateMediaNotFoundTest() {
        String id = "999";

        Media updatedMedia = new Media();
        updatedMedia.setName("One Piece");

        when(mediaRepository.findById(id))
                .thenReturn(java.util.Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> mediaService.updateMedia(id, updatedMedia)
        );

        verify(mediaRepository).findById(id);
        verify(mediaRepository, never()).save(any(Media.class));
    }

    @Test
    void DeleteMediaTest(){
        String id = "999";

        when(mediaRepository.existsById(id)).thenReturn(true);

        mediaService.deleteMedia(id);

        verify(mediaRepository).existsById(id);
        verify(mediaRepository).deleteById(id);
    }

    @Test
    void DeleteMediaByIdNotFound(){
        String id = "999";
        when(mediaRepository.existsById(id)).thenReturn(false);
        assertThrows(
                ResourceNotFoundException.class,
                () -> mediaService.deleteMedia(id)
        );

        verify(mediaRepository).existsById(id);
        verify(mediaRepository,never()).deleteById(id);
    }
}

