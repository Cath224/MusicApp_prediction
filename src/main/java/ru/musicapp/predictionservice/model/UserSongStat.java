package ru.musicapp.predictionservice.predictionservice.model;

import lombok.*;


import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class UserSongStat {

    private String source;
    private UUID songId;
    private UUID userId;

}
