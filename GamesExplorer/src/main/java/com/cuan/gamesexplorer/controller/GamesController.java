package com.cuan.gamesexplorer.controller;

import com.cuan.gamesexplorer.dtos.BaseResponseDto;
import com.cuan.gamesexplorer.model.Game;
import com.cuan.gamesexplorer.service.GamesApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/games")
public class GamesController {
    private final String successMessage;
    private final GamesApiService gamesApiService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GamesController(String successMessage, GamesApiService gamesApiService) {
        this.successMessage = successMessage;
        this.gamesApiService = gamesApiService;
    }

    // Get a full list of games
    @GetMapping("/list")
    public ResponseEntity<?> getGamesList() {
        try {
            List<Game> res = gamesApiService.getGamesList().get(10, TimeUnit.SECONDS);
            return ResponseEntity.ok(new BaseResponseDto(successMessage, objectMapper.writeValueAsString(res)));
        } catch (JsonProcessingException |InterruptedException | TimeoutException | ExecutionException e) {
            return ResponseEntity.internalServerError().body(new BaseResponseDto(e.getMessage(), null));
        }
    }

    // Filter games based on some keyValues - json input
    @PostMapping("/filter")
    public ResponseEntity<?> filterGames() {
        //TODO: implement this
        return ResponseEntity.ok(successMessage);
    }
}
