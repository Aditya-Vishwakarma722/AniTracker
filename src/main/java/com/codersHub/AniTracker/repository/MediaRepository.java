package com.codersHub.AniTracker.repository;

import com.codersHub.AniTracker.entity.Media;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MediaRepository extends MongoRepository<Media, String> {
}
