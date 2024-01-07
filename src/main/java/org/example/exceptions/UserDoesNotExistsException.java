package org.example.exceptions;

import org.example.Actions;

public class UserDoesNotExistsException extends Exception{
    public static final String message = Actions.ANSI_LIGHT_RED + "This user doesn't exist! Try again.\n" + Actions.ANSI_RESET;

    public UserDoesNotExistsException() {super(message);}
}
