package com.codersHub.AniTracker;

import com.codersHub.AniTracker.dto.MediaResponse;
import com.codersHub.AniTracker.entity.Media;
import com.codersHub.AniTracker.repository.MediaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
class MediaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MediaRepository mediaRepository;

    @Test
    void createMediaIntegrationTest() throws Exception {
        String requestBody = """
                {
                    "name": "Integration Test Anime",
                    "description": "Created during an integration test",
                    "type": "ANIME",
                    "status": "COMPLETED",
                    "rating": 9.0
                }
                """;

        String responseBody = mockMvc.perform(post("/api/media")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MediaResponse response =
                objectMapper.readValue(responseBody, MediaResponse.class);

        assertNotNull(response.getId());
        assertEquals("Integration Test Anime", response.getName());
        assertEquals("ANIME", response.getType().name());
        assertEquals("COMPLETED", response.getStatus().name());

        try {
            Media savedMedia = mediaRepository.findById(response.getId())
                    .orElseThrow();

            assertEquals("Integration Test Anime", savedMedia.getName());
            assertEquals(9.0, savedMedia.getRating());

        } finally {
            mediaRepository.deleteById(response.getId());
        }
    }


    @Test
    void getAllMediaIntegrationTest() throws Exception {

        String requestBody = """
            {
                "name": "Integration Test Anime",
                "description": "Created during an integration test",
                "type": "ANIME",
                "status": "COMPLETED",
                "rating": 9.0
            }
            """;

        String responseBody = mockMvc.perform(post("/api/media")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MediaResponse createdMedia =
                objectMapper.readValue(responseBody, MediaResponse.class);

        try {
            mockMvc.perform(get("/api/media"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(
                            "$[*].name",
                            hasItem("Integration Test Anime")
                    ));

        } finally {
            mediaRepository.deleteById(createdMedia.getId());
        }
    }


    @Test
    void getMediaByIdIntegrationTest() throws Exception {

        String requestBody = """
            {
                "name": "Find By ID Anime",
                "description": "Testing retrieval by ID",
                "type": "ANIME",
                "status": "COMPLETED",
                "rating": 9.0
            }
            """;

        String responseBody = mockMvc.perform(post("/api/media")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MediaResponse createdMedia =
                objectMapper.readValue(responseBody, MediaResponse.class);

        try {
            mockMvc.perform(get("/api/media/" + createdMedia.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id")
                            .value(createdMedia.getId()))
                    .andExpect(jsonPath("$.name")
                            .value("Find By ID Anime"))
                    .andExpect(jsonPath("$.rating")
                            .value(9.0));

        } finally {
            mediaRepository.deleteById(createdMedia.getId());
        }
    }


    @Test
    void updateMediaIntegrationTest() throws Exception {

        String requestBody = """
            {
                "name": "Anime Before Update",
                "description": "Original description",
                "type": "ANIME",
                "status": "COMPLETED",
                "rating": 7.0
            }
            """;

        String responseBody = mockMvc.perform(post("/api/media")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MediaResponse createdMedia =
                objectMapper.readValue(responseBody, MediaResponse.class);

        try {
            String updateBody = """
                {
                    "name": "Anime After Update",
                    "description": "Updated description",
                    "type": "ANIME",
                    "status": "COMPLETED",
                    "rating": 9.5
                }
                """;

            mockMvc.perform(put("/api/media/" + createdMedia.getId())
                            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                            .content(updateBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name")
                            .value("Anime After Update"))
                    .andExpect(jsonPath("$.rating").value(9.5));

            Media savedMedia = mediaRepository
                    .findById(createdMedia.getId())
                    .orElseThrow();

            assertEquals("Anime After Update", savedMedia.getName());
            assertEquals("Updated description", savedMedia.getDescription());
            assertEquals(9.5, savedMedia.getRating());

        } finally {
            mediaRepository.deleteById(createdMedia.getId());
        }
    }



    @Test
    void deleteMediaIntegrationTest() throws Exception {

        String requestBody = """
            {
                "name": "Anime To Delete",
                "description": "Testing deletion",
                "type": "ANIME",
                "status": "COMPLETED",
                "rating": 8.0
            }
            """;

        String responseBody = mockMvc.perform(post("/api/media")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MediaResponse createdMedia =
                objectMapper.readValue(responseBody, MediaResponse.class);

        String mediaId = createdMedia.getId();

        mockMvc.perform(delete("/api/media/" + mediaId))
                .andExpect(status().isNoContent());

        assertTrue(mediaRepository.findById(mediaId).isEmpty());
    }

}