package com.cuan.gamesexplorer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Game{
    // Set default value to -1 to avoid searching for games with id = 0 if not set
    private long id = -1;
    private String title;
    private String thumbnail;
    private String shortDescription;
    private String gameURL;
    private String genre;
    private String platform;
    private String publisher;
    private String developer;
    private String releaseDate;
    private String freetogameProfileURL;
}
