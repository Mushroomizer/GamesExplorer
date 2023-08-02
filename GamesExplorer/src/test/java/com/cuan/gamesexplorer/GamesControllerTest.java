package com.cuan.gamesexplorer;

import com.cuan.gamesexplorer.controller.GamesController;
import com.cuan.gamesexplorer.dtos.BaseResponseDto;
import com.cuan.gamesexplorer.dtos.FilterRequestDto;
import com.cuan.gamesexplorer.model.Game;
import com.cuan.gamesexplorer.service.GamesApiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GamesControllerTest {

    static {
        System.setProperty("GAMES_EXPLORER_HOST", "host");
        System.setProperty("GAMES_EXPLORER_API_KEY", "key");
    }

    private MockMvc mockMvc;

    @Mock
    private GamesApiService gamesApiService;

    @InjectMocks
    private GamesController gamesController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gamesController).build();
    }

    @Test
    public void testGetGamesList() throws Exception {
        // Arrange
        List<Game> gamesList = Arrays.asList(
                new Game(1, "Game 1", "thumb1.jpg", "Description 1", "game1.com", "Genre 1", "Platform 1", "Publisher 1", "Developer 1", "2023-08-01", "profile1.com"),
                new Game(2, "Game 2", "thumb2.jpg", "Description 2", "game2.com", "Genre 2", "Platform 2", "Publisher 2", "Developer 2", "2023-08-02", "profile2.com")
        );

        Future<List<Game>> futureGamesList = CompletableFuture.completedFuture(gamesList);

        // Mock the behavior of gamesApiService.getGamesList() to return the Future
        when(gamesApiService.getGamesList()).thenReturn(futureGamesList);

        // Act & Assert
        mockMvc.perform(get("/api/games/list"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    BaseResponseDto responseDto = objectMapper.readValue(jsonResponse, BaseResponseDto.class);
                    assertThat(responseDto.getData()).isNotNull();

                    List<Game> actualGamesList = objectMapper.readValue(responseDto.getData(), new TypeReference<>() {});
                    assertThat(actualGamesList).hasSize(2);
                    assertThat(actualGamesList.get(0).getTitle()).isEqualTo("Game 1");
                    assertThat(actualGamesList.get(1).getTitle()).isEqualTo("Game 2");
                });
    }

    @Test
    public void testFilterGames() throws Exception {
        // Arrange
        List<Game> gamesList = Arrays.asList(
                new Game(1, "Game 1", "thumb1.jpg", "Description 1", "game1.com", "Genre 1", "Platform 1", "Publisher 1", "Developer 1", "2023-08-01", "profile1.com"),
                new Game(2, "Game 2", "thumb2.jpg", "Description 2", "game2.com", "Genre 2", "Platform 2", "Publisher 2", "Developer 2", "2023-08-02", "profile2.com")
        );

        Future<List<Game>> futureGamesList = CompletableFuture.completedFuture(gamesList);

        // Mock the behavior of gamesApiService.getGamesList() to return the Future
        when(gamesApiService.getGamesList()).thenReturn(futureGamesList);

        // Act
        FilterRequestDto filterRequestDto = new FilterRequestDto();
        filterRequestDto.setPlatform("Platform 1");

        // Act & Assert
        mockMvc.perform(post("/api/games/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filterRequestDto)))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    BaseResponseDto responseDto = objectMapper.readValue(jsonResponse, BaseResponseDto.class);
                    assertThat(responseDto.getData()).isNotNull();

                    // Deserialize the data field into the actual Game[]
                    Game[] filteredGames = objectMapper.readValue(responseDto.getData(), Game[].class);
                    assertThat(filteredGames).hasSize(1);
                    assertThat(filteredGames[0].getTitle()).isEqualTo("Game 1");
                });
    }
}
