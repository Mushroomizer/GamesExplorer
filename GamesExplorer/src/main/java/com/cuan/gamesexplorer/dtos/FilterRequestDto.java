package com.cuan.gamesexplorer.dtos;

import com.cuan.gamesexplorer.model.Game;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterRequestDto extends Game {

    public Game[] ApplyFilter(Game[] games) {
        // Copy array to avoid modifying the original array
        Game[] filteredGames = Arrays.copyOf(games, games.length);

        Field[] fields = Game.class.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                // if field name is id, check that it is not -1
                if (field.getName().equalsIgnoreCase("id") && field.getLong(this) == -1) {
                    continue;
                }

                if (field.get(this) != null) {
                    filteredGames = FilterByProperty(field.getName(), field.get(this).toString(), filteredGames);
                    if (filteredGames.length <= 1) {
                        break;
                    }
                }
            } catch (Exception ignored) {
            }
        }
        return filteredGames;
    }

    private Game[] FilterByProperty(String propertyName, String propertyValue, Game[] games) {
        List<Game> filteredGames = new ArrayList<>();
        for (Game game : games) {
            try {
                Field field = Game.class.getDeclaredField(propertyName);
                field.setAccessible(true);
                if (field.get(game).toString().toLowerCase().contains(propertyValue.toLowerCase())) {
                    filteredGames.add(game);
                }
            } catch (Exception ignored) {
            }
        }
        return filteredGames.toArray(new Game[0]);
    }
}
