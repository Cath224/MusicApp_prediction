package ru.musicapp.predictionservice.predictionservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.predictionservice.predictionservice.dao.UserSongStatDao;
import ru.musicapp.predictionservice.predictionservice.model.UserSongStat;
import ru.musicapp.predictionservice.predictionservice.service.UserSongStatService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSongStatServiceImpl implements UserSongStatService {

    private final UserSongStatDao dao;

    @Transactional(isolation = Isolation.SERIALIZABLE, readOnly = true)
    @Override
    public Set<UUID> generateSimilarUsers(UUID userId) {
        List<UserSongStat> likes = dao.getUserStatLikesByUserId(userId);
        List<UserSongStat> history = dao.getUserStatHistoryByUserId(userId);

        Set<UUID> likesSongIds = likes.stream()
                .map(UserSongStat::getSongId)
                .collect(Collectors.toSet());

        Set<UUID> historySongIds = history.stream()
                .map(UserSongStat::getSongId)
                .collect(Collectors.toSet());


        List<UserSongStat> otherUsersLikes = dao.getRandomUserStatsLikesAndNotUserById(userId);
        List<UserSongStat> otherUsersHistory = dao.getRandomUserStatsHistoryAndNotUserById(userId);

        Map<UUID, List<UserSongStat>> likesByUserIds = otherUsersLikes.stream()
                .collect(Collectors.groupingBy(UserSongStat::getUserId));


        Map<UUID, List<UserSongStat>> historyByUserIds = otherUsersHistory.stream()
                .collect(Collectors.groupingBy(UserSongStat::getUserId));


        Map<UUID, Double> similarityByUserId = new HashMap<>(1000);

        likesByUserIds.forEach((key, values) -> {
            List<UUID> otherLikesIds = values.stream()
                    .map(UserSongStat::getSongId)
                    .toList();

            for (UUID otherLikesId : otherLikesIds) {
                if (likesSongIds.contains(otherLikesId)) {
                    similarityByUserId.compute(key, (id, similarityCount) -> {
                        Double similarityCountLocal = similarityCount;
                        if (similarityCountLocal == null) {
                            similarityCountLocal = 0D;
                        }
                        similarityCountLocal += 1;
                        return similarityCountLocal;
                    });
                }
            }
        });

        historyByUserIds.forEach((key, values) -> {
            List<UUID> otherHistoryIds = values.stream()
                    .map(UserSongStat::getSongId)
                    .toList();

            for (UUID otherHistoryId : otherHistoryIds) {
                if (historySongIds.contains(otherHistoryId)) {
                    similarityByUserId.compute(key, (id, similarityCount) -> {
                        Double similarityCountLocal = similarityCount;
                        if (similarityCountLocal == null) {
                            similarityCountLocal = 0D;
                        }
                        similarityCountLocal += 0.5;
                        return similarityCountLocal;
                    });
                }
            }
        });

        return similarityByUserId.entrySet().stream()
                .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                .limit(50)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
