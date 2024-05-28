package ru.musicapp.predictionservice.predictionservice.service;

import java.util.Set;
import java.util.UUID;

public interface UserSongStatService {

    Set<UUID> generateSimilarUsers(UUID userId);

}
