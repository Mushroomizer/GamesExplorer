package com.cuan.gamesexplorer.controller;

import com.cuan.gamesexplorer.model.Game;
import com.cuan.gamesexplorer.service.GamesApiService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Controller
public class ViewController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(ViewController.class);
    private final GamesApiService gamesApiService;

    public ViewController(GamesApiService gamesApiService) {
        this.gamesApiService = gamesApiService;
    }

    @GetMapping("/")
    public String showGamesList(Model model) throws ExecutionException, InterruptedException, TimeoutException {
        List<Game> res = gamesApiService.getGamesList().get(10, TimeUnit.SECONDS);
        log.info("Retrieved [{}] games", res.size());
        model.addAttribute("games", res);
        return "index";
    }
}
