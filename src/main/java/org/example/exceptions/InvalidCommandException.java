package org.example.exceptions;

import org.example.Actions;

public class InvalidCommandException extends Exception{
    public static final String message = Actions.ANSI_LIGHT_RED + "Invalid command! Try again.\n" + Actions.ANSI_RESET;

    public InvalidCommandException(String s) {
        super(Actions.ANSI_LIGHT_RED + s + Actions.ANSI_RESET);
    }
}
