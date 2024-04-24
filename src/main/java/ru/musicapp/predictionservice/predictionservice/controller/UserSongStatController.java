package ru.musicapp.predictionservice.predictionservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.musicapp.predictionservice.predictionservice.service.UserSongStatService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/private/user-stat")
@RequiredArgsConstructor
public class UserSongStatController {

    private final UserSongStatService service;

    @PostMapping("{userId}")
    public Set<UUID> generateSimilarUsersIds(@PathVariable UUID userId) {
        return service.generateSimilarUsers(userId);
    }

}
