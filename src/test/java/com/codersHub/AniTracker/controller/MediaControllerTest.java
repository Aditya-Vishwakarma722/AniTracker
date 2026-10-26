package com.codersHub.AniTracker.controller;

import com.codersHub.AniTracker.entity.Media;
import com.codersHub.AniTracker.exception.GlobalExceptionHandler;
import com.codersHub.AniTracker.exception.ResourceNotFoundException;
import com.codersHub.AniTracker.mapper.MediaMapper;
import com.codersHub.AniTracker.service.MediaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import com.codersHub.AniTracker.dto.MediaResponse;
import com.codersHub.AniTracker.entity.MediaStatus;
import com.codersHub.AniTracker.entity.MediaType;
import com.codersHub.AniTracker.dto.MediaRequest;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(MockitoExtension.class)
public class MediaControllerTest {

    @Mock
    private MediaService mediaService;

    @Mock
    private MediaMapper mediaMapper;

    @InjectMocks
    private MediaController mediaController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(mediaController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }



    @Test
    void GetMediaTest() throws Exception {
        com.codersHub.AniTracker.entity.Media media =
                new com.codersHub.AniTracker.entity.Media();

        media.setId("123");
        media.setName("Attack on Titan");
        media.setDescription("Humanity fights against Titans");
        media.setType(MediaType.ANIME);
        media.setStatus(MediaStatus.COMPLETED);
        media.setRating(9.0);

        MediaResponse response = new MediaResponse();
        response.setId("123");
        response.setName("Attack on Titan");
        response.setDescription("Humanity fights against Titans");
        response.setType(MediaType.ANIME);
        response.setStatus(MediaStatus.COMPLETED);
        response.setRating(9.0);

        when(mediaService.GetAllMedia()).thenReturn(List.of(media));
        when(mediaMapper.toMediaResponse(media)).thenReturn(response);

        mockMvc.perform(get("/api/media"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].id").value("123"))
                .andExpect(jsonPath("$[0].name").value("Attack on Titan"))
                .andExpect(jsonPath("$[0].description")
                        .value("Humanity fights against Titans"))
                .andExpect(jsonPath("$[0].type").value("ANIME"))
                .andExpect(jsonPath("$[0].status").value("COMPLETED"))
                .andExpect(jsonPath("$[0].rating").value(9.0));
    }

    @Test
    void GetMediaByIdTest() throws Exception{
        Media media = new Media();

        media.setId("123");
        media.setName("Attack on Titan");
        media.setDescription("Humanity fights against Titans");
        media.setType(MediaType.ANIME);
        media.setStatus(MediaStatus.COMPLETED);
        media.setRating(9.0);

        MediaResponse response = new MediaResponse();
        response.setId("123");
        response.setName("Attack on Titan");
        response.setDescription("Humanity fights against Titans");
        response.setType(MediaType.ANIME);
        response.setStatus(MediaStatus.COMPLETED);
        response.setRating(9.0);

        when(mediaService.getMediaById("123")).thenReturn(media);
        when(mediaMapper.toMediaResponse(media)).thenReturn(response);

        mockMvc.perform(get("/api/media/123"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("Attack on Titan"))
                .andExpect(jsonPath("$.description")
                        .value("Humanity fights against Titans"))
                .andExpect(jsonPath("$.type").value("ANIME"))
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.rating").value(9.0));

        verify(mediaService).getMediaById("123");
    }


    @Test
    void CreateMediaTest() throws Exception {
        Media media = new Media();
        media.setName("Attack on Titan");
        media.setDescription("Humanity fights against Titans");
        media.setType(MediaType.ANIME);
        media.setStatus(MediaStatus.COMPLETED);
        media.setRating(9.0);

        Media savedMedia = new Media();
        savedMedia.setId("123");
        savedMedia.setName("Attack on Titan");
        savedMedia.setDescription("Humanity fights against Titans");
        savedMedia.setType(MediaType.ANIME);
        savedMedia.setStatus(MediaStatus.COMPLETED);
        savedMedia.setRating(9.0);

        MediaResponse response = new MediaResponse();
        response.setId("123");
        response.setName("Attack on Titan");
        response.setDescription("Humanity fights against Titans");
        response.setType(MediaType.ANIME);
        response.setStatus(MediaStatus.COMPLETED);
        response.setRating(9.0);

        when(mediaMapper.toMedia(any(MediaRequest.class)))
                .thenReturn(media);

        when(mediaService.SaveMedia(media))
                .thenReturn(savedMedia);

        when(mediaMapper.toMediaResponse(savedMedia))
                .thenReturn(response);

        mockMvc.perform(post("/api/media")
                        .contentType(APPLICATION_JSON)
                        .content("""
                            {
                                "name": "Attack on Titan",
                                "description": "Humanity fights against Titans",
                                "type": "ANIME",
                                "status": "COMPLETED",
                                "rating": 9.0
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("Attack on Titan"))
                .andExpect(jsonPath("$.description")
                        .value("Humanity fights against Titans"))
                .andExpect(jsonPath("$.type").value("ANIME"))
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.rating").value(9.0));

        verify(mediaMapper).toMedia(any(MediaRequest.class));
        verify(mediaService).SaveMedia(media);
        verify(mediaMapper).toMediaResponse(savedMedia);
    }


    @Test
    void UpdateMediaTest() throws Exception {
        Media media = new Media();
        media.setName("Attack on Titan Final Season");
        media.setDescription("The final battle for humanity");
        media.setType(MediaType.ANIME);
        media.setStatus(MediaStatus.COMPLETED);
        media.setRating(10.0);

        Media updatedMedia = new Media();
        updatedMedia.setId("123");
        updatedMedia.setName("Attack on Titan Final Season");
        updatedMedia.setDescription("The final battle for humanity");
        updatedMedia.setType(MediaType.ANIME);
        updatedMedia.setStatus(MediaStatus.COMPLETED);
        updatedMedia.setRating(10.0);

        MediaResponse response = new MediaResponse();
        response.setId("123");
        response.setName("Attack on Titan Final Season");
        response.setDescription("The final battle for humanity");
        response.setType(MediaType.ANIME);
        response.setStatus(MediaStatus.COMPLETED);
        response.setRating(10.0);

        when(mediaMapper.toMedia(any(MediaRequest.class)))
                .thenReturn(media);

        when(mediaService.updateMedia("123", media))
                .thenReturn(updatedMedia);

        when(mediaMapper.toMediaResponse(updatedMedia))
                .thenReturn(response);

        mockMvc.perform(put("/api/media/123")
                        .contentType(APPLICATION_JSON)
                        .content("""
                            {
                                "name": "Attack on Titan Final Season",
                                "description": "The final battle for humanity",
                                "type": "ANIME",
                                "status": "COMPLETED",
                                "rating": 10.0
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name")
                        .value("Attack on Titan Final Season"))
                .andExpect(jsonPath("$.description")
                        .value("The final battle for humanity"))
                .andExpect(jsonPath("$.type").value("ANIME"))
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.rating").value(10.0));

        verify(mediaMapper).toMedia(any(MediaRequest.class));
        verify(mediaService).updateMedia("123", media);
        verify(mediaMapper).toMediaResponse(updatedMedia);
    }


    @Test
    void DeleteMediaTest() throws Exception {
        mockMvc.perform(delete("/api/media/123"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(mediaService).deleteMedia("123");
    }


    @Test
    void CreateMediaWithBlankNameTest() throws Exception {
        String requestBody = """
            {
                "name": "",
                "description": "Humanity fights against Titans",
                "type": "ANIME",
                "status": "COMPLETED",
                "rating": 9.0
            }
            """;

        mockMvc.perform(post("/api/media")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(mediaService);
    }


    @Test
    void CreateMediaWithInvalidRatingTest() throws Exception {
        String requestBody = """
            {
                "name": "Attack on Titan",
                "description": "Humanity fights against Titans",
                "type": "ANIME",
                "status": "COMPLETED",
                "rating": 11.0
            }
            """;

        mockMvc.perform(post("/api/media")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediaService);
    }


    @Test
    void CreateMediaWithNegativeRatingTest() throws Exception {
        String requestBody = """
            {
                "name": "Attack on Titan",
                "description": "Humanity fights against Titans",
                "type": "ANIME",
                "status": "COMPLETED",
                "rating": -1.0
            }
            """;

        mockMvc.perform(post("/api/media")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediaService);
    }


    @Test
    void CreateMediaWithoutTypeTest() throws Exception {
        String requestBody = """
            {
                "name": "Attack on Titan",
                "description": "Humanity fights against Titans",
                "status": "COMPLETED",
                "rating": 9.0
            }
            """;

        mockMvc.perform(post("/api/media")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediaService);
    }


    @Test
    void CreateMediaWithoutStatusTest() throws Exception {
        String requestBody = """
            {
                "name": "Attack on Titan",
                "description": "Humanity fights against Titans",
                "type": "ANIME",
                "rating": 9.0
            }
            """;

        mockMvc.perform(post("/api/media")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediaService);
    }


    @Test
    void CreateMediaWithInvalidTypeTest() throws Exception {
        String requestBody = """
            {
                "name": "Attack on Titan",
                "description": "Humanity fights against Titans",
                "type": "BOOK",
                "status": "COMPLETED",
                "rating": 9.0
            }
            """;

        mockMvc.perform(post("/api/media")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediaService);
    }


    @Test
    void GetMediaByIdNotFoundTest() throws Exception {
        String id = "999";

        when(mediaService.getMediaById(id))
                .thenThrow(new ResourceNotFoundException("Media not found with id: " + id));

        mockMvc.perform(get("/api/media/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Media not found with id: " + id));
    }


    @Test
    void CreateMediaWithInvalidTypeReturnsErrorDetailsTest() throws Exception {
        String requestBody = """
            {
                "name": "Attack on Titan",
                "description": "Humanity fights against Titans",
                "type": "BOOK",
                "status": "COMPLETED",
                "rating": 9.0
            }
            """;

        mockMvc.perform(post("/api/media")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Invalid request value"))
                .andExpect(jsonPath("$.errors.type").exists())
                .andExpect(jsonPath("$.errors.type").value(
                        org.hamcrest.Matchers.containsString("BOOK")
                ))
                .andExpect(jsonPath("$.errors.type").value(
                        org.hamcrest.Matchers.containsString("ANIME")
                ));
    }

    @Test
    void DeleteMediaNotFoundTest() throws Exception {
        String id = "999";

        doThrow(new ResourceNotFoundException("Media not found with id: " + id))
                .when(mediaService).deleteMedia(id);

        mockMvc.perform(delete("/api/media/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Media not found with id: " + id));
    }

    @Test
    void UpdateMediaNotFoundTest() throws Exception {
        String id = "999";

        String requestBody = """
            {
                "name": "Attack on Titan",
                "description": "Humanity fights against Titans",
                "type": "ANIME",
                "status": "COMPLETED",
                "rating": 9.0
            }
            """;

        when(mediaMapper.toMedia(any(MediaRequest.class)))
                .thenReturn(new Media());

        when(mediaService.updateMedia(eq(id), any(Media.class)))
                .thenThrow(new ResourceNotFoundException(
                        "Media not found with id: " + id));

        mockMvc.perform(put("/api/media/" + id)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Media not found with id: " + id));
    }

    @Test
    void CreateMediaWithMalformedJsonTest() throws Exception {
        String requestBody = """
            {
                "name": "Attack on Titan",
                "type": "ANIME",
                "status": "COMPLETED",
                "rating": 9.0,
            }
            """;

        mockMvc.perform(post("/api/media")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message")
                        .value("Invalid request value"))
                .andExpect(jsonPath("$.errors.body").exists());

        verifyNoInteractions(mediaService);
    }

}
