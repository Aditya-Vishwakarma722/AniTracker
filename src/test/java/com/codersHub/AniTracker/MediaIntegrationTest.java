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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
}