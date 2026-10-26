package com.codersHub.AniTracker.controller;

import com.codersHub.AniTracker.entity.Media;
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

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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


}
