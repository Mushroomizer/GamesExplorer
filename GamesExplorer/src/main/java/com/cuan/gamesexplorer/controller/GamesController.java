package com.cuan.gamesexplorer.controller;

import com.cuan.gamesexplorer.dtos.BaseResponseDto;
import com.cuan.gamesexplorer.dtos.FilterRequestDto;
import com.cuan.gamesexplorer.model.Game;
import com.cuan.gamesexplorer.service.GamesApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/games")
public class GamesController {
    private final Logger log = LoggerFactory.getLogger(GamesController.class);
    private final String successMessage;
    private final GamesApiService gamesApiService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GamesController(String successMessage, GamesApiService gamesApiService) {
        this.successMessage = successMessage;
        this.gamesApiService = gamesApiService;
    }

    // Get a full list of games
    @GetMapping("/list")
    public ResponseEntity<BaseResponseDto> getGamesList() {
        try {
            List<Game> res = gamesApiService.getGamesList().get(10, TimeUnit.SECONDS);
            log.info("Retrieved [{}] games", res.size());
            return ResponseEntity.ok(new BaseResponseDto(successMessage, objectMapper.writeValueAsString(res)));
        } catch (JsonProcessingException | InterruptedException | TimeoutException | ExecutionException e) {
            return ResponseEntity.internalServerError().body(new BaseResponseDto(e.getMessage(), null));
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<BaseResponseDto> filterGames(@RequestBody FilterRequestDto filterRequestDto) {
        try {
            List<Game> res = gamesApiService.getGamesList().get(10, TimeUnit.SECONDS);
            Game[] filteredGames = filterRequestDto.ApplyFilter(res.toArray(new Game[0]));
            log.info("Filtered [{}] games", filteredGames.length);
            return ResponseEntity.ok(new BaseResponseDto(successMessage, objectMapper.writeValueAsString(filteredGames)));
        } catch (JsonProcessingException | InterruptedException | TimeoutException | ExecutionException e) {
            return ResponseEntity.internalServerError().body(new BaseResponseDto(e.getMessage(), null));
        }
    }
}
