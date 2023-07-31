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
    private long id;
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
