package ru.musicapp.predictionservice.predictionservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class UserSongStatConfiguration {

    @Bean("userStatJdbcTemplate")
    public JdbcTemplate userSongStatJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
