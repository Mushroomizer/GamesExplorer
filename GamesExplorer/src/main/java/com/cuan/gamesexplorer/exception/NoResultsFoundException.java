package com.cuan.gamesexplorer.exception;

public class NoResultsFoundException extends RuntimeException{

    public NoResultsFoundException() {
        super("No results found");
    }
}
