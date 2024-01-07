package org.example.exceptions;

import org.example.Actions;

public class PageDoesntExistException extends Exception{
    public static final String message = Actions.ANSI_LIGHT_RED + "This page doesn't exist! Try again.\n" + Actions.ANSI_RESET;

    public PageDoesntExistException() {super(message);}
}
