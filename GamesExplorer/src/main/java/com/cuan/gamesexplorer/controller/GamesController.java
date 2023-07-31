package com.cuan.gamesexplorer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/games")
public class GamesController {

    private final ResponseEntity<?> httpSuccessMessage;

    public GamesController(ResponseEntity<?> httpSuccessMessage) {
        this.httpSuccessMessage = httpSuccessMessage;
    }

    // Get a full list of games
    @GetMapping("/list")
    public ResponseEntity<?> getGamesList() {
        //TODO: implement this
        return httpSuccessMessage;
    }

    // Filter games based on some keyValues - json input
    @PostMapping("/filter")
    public ResponseEntity<?> filterGames() {
        //TODO: implement this
        return httpSuccessMessage;
    }
}
