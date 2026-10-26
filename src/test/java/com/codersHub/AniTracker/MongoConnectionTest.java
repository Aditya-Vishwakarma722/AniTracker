package com.codersHub.AniTracker;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MongoConnectionTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void MongoConnectionTest() {
        assertNotNull(mongoTemplate);

        // Check the MongoDB connection
        Document pingResult = mongoTemplate.getDb()
                .runCommand(new Document("ping", 1));

        assertEquals(1, ((Number) pingResult.get("ok")).intValue());

        // Create a unique test document
        String testId = UUID.randomUUID().toString();

        Document testDocument = new Document("_id", testId)
                .append("name", "AniTracker Test");

        try {
            // Insert the document
            mongoTemplate.insert(testDocument, "connection_test");

            // Retrieve the document
            Document savedDocument = mongoTemplate.findById(
                    testId, Document.class, "connection_test"
            );

            // Verify the saved data
            assertNotNull(savedDocument);
            assertEquals("AniTracker Test", savedDocument.getString("name"));

        } finally {
            // Clean up the test document
            mongoTemplate.remove(
                    new Document("_id", testId), "connection_test"
            );
        }
    }
}