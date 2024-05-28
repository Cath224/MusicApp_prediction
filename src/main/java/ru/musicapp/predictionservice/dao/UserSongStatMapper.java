package ru.musicapp.predictionservice.predictionservice.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.musicapp.predictionservice.predictionservice.model.UserSongStat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Component
public class UserSongStatMapper implements RowMapper<UserSongStat> {


    @Override
    public UserSongStat mapRow(ResultSet rs, int rowNum) throws SQLException {

        return UserSongStat.builder()
                .userId(UUID.fromString(rs.getString("user_id")))
                .songId(UUID.fromString(rs.getString("song_id")))
                .source(rs.getString("source"))
                .build();
    }


}
