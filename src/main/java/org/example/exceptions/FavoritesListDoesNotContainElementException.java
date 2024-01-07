package org.example.exceptions;

import org.example.Actions;

public class FavoritesListDoesNotContainElementException extends Exception{
    public static final String message = Actions.ANSI_LIGHT_RED + "This page doesn't exist in your favorites list! Try again.\n" + Actions.ANSI_RESET;

    public FavoritesListDoesNotContainElementException() {super(message);}
}