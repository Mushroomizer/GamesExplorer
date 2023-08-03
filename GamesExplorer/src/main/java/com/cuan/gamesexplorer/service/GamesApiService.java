package com.cuan.gamesexplorer.service;

import com.cuan.gamesexplorer.httpinterceptor.GamesApiHttpRequestInterceptor;
import com.cuan.gamesexplorer.model.Game;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@CacheConfig(cacheNames = "games")
public class GamesApiService {

    @Value("${games.api.host}")
    private String apiHost;

    @Value("${games.api.key}")
    private String apiKey;

    @Value("${games.api.threadpool.size:10}")
    private int threadPoolSize;

    private ThreadPoolExecutor executor;

    private final RestTemplate restTemplate = new RestTemplate();


    // Need to use @PostConstruct to initialize the executor and the restTemplate, because @Value is not available in the constructor
    @PostConstruct
    private void initialize(){
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new GamesApiHttpRequestInterceptor(Map.of("X-RapidAPI-Key", apiKey, "X-RapidAPI-Host", apiHost)));
        restTemplate.setInterceptors(interceptors);
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolSize);
    }

    @Cacheable
    public Future<List<Game>> getGamesList() {
        return executor.submit(() -> {
            try {
                ResponseEntity<Game[]> gamesListResponse = restTemplate.getForEntity("https://" + apiHost + "/api/games", Game[].class);
                gamesListResponse.getStatusCode();
                if (gamesListResponse.getStatusCode().isError()) {
                    //TODO: handle error
                    return null;
                }

                Game[] res = gamesListResponse.getBody();
                if (res == null) {
                    return null;
                }

                return Arrays.asList(res);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        });

    }

}
