package com.cuan.gamesexplorer;
import com.cuan.gamesexplorer.model.Game;
import com.cuan.gamesexplorer.service.GamesApiService;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GamesApiServiceTest {

    private static WireMockServer wireMockServer;

    @InjectMocks
    private GamesApiService gamesApiService;

    @BeforeAll
    public static void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor("http://localhost", 8080);
    }

    @AfterAll
    public static void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void testGetGamesList() throws ExecutionException, InterruptedException {
        wireMockServer.stubFor(get(urlEqualTo("/api/games"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\": 1, \"title\": \"Game 1\", \"thumbnail\": \"thumb1.jpg\", \"shortDescription\": \"Description 1\", \"gameURL\": \"game1.com\", \"genre\": \"Genre 1\", \"platform\": \"Platform 1\", \"publisher\": \"Publisher 1\", \"developer\": \"Developer 1\", \"releaseDate\": \"2023-08-01\", \"freetogameProfileURL\": \"profile1.com\"}, " +
                                "{\"id\": 2, \"title\": \"Game 2\", \"thumbnail\": \"thumb2.jpg\", \"shortDescription\": \"Description 2\", \"gameURL\": \"game2.com\", \"genre\": \"Genre 2\", \"platform\": \"Platform 2\", \"publisher\": \"Publisher 2\", \"developer\": \"Developer 2\", \"releaseDate\": \"2023-08-02\", \"freetogameProfileURL\": \"profile2.com\"}]")));

        Future<List<Game>> futureGamesList = gamesApiService.getGamesList();

        List<Game> gamesList = futureGamesList.get();
        assertThat(gamesList).isNotNull();
        assertThat(gamesList).hasSize(2);
        assertThat(gamesList.get(0).getTitle()).isEqualTo("Game 1");
        assertThat(gamesList.get(1).getTitle()).isEqualTo("Game 2");
    }
}
