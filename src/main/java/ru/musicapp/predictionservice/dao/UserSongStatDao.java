package ru.musicapp.predictionservice.predictionservice.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.musicapp.predictionservice.predictionservice.model.UserSongStat;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserSongStatDao {

    private final UserSongStatMapper rowMapper;

    @Autowired
    @Qualifier("userStatJdbcTemplate")
    private JdbcTemplate jdbcTemplate;


    public List<UserSongStat> getUserStatLikesByUserId(UUID userId) {
        return jdbcTemplate.query("select * from user_prediction.user_song_stat where user_id = ?::uuid and source = 'likes' limit 100", (ps) -> {
            ps.setString(1, userId.toString());
        }, rowMapper);
    }

    public List<UserSongStat> getUserStatHistoryByUserId(UUID userId) {
        return jdbcTemplate.query("select * from user_prediction.user_song_stat where user_id = ?::uuid and source = 'history' limit 100", (ps) -> {
            ps.setString(1, userId.toString());
        }, rowMapper);
    }

    public List<UserSongStat> getRandomUserStatsLikesAndNotUserById(UUID userId) {
        return jdbcTemplate.query("select * from user_prediction.user_song_stat " +
                        "where user_id in (select user_id from user_prediction.user_song_stat where user_id != ?::uuid group by user_id limit 1000) " +
                        "and source = 'likes' " +
                        "limit 200000",
                (ps) -> ps.setString(1, userId.toString()), rowMapper);
    }

    public List<UserSongStat> getRandomUserStatsHistoryAndNotUserById(UUID userId) {
        return jdbcTemplate.query("select * from user_prediction.user_song_stat " +
                        "where user_id in (select user_id from user_prediction.user_song_stat where user_id != ?::uuid group by user_id limit 1000) " +
                        "and source = 'history' " +
                        "limit 200000",
                (ps) -> ps.setString(1, userId.toString()), rowMapper);
    }
}
