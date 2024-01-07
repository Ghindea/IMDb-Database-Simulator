package org.example.exceptions;

import org.example.Actions;

public class InvalidOptionException extends Exception{
    public static final String message = Actions.ANSI_LIGHT_RED + "Invalid option! Try again.\n" + Actions.ANSI_RESET;

    public InvalidOptionException() {super(message);}
}
