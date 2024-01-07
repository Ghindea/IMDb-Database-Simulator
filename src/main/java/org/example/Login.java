package org.example;

import java.util.Scanner;

public class Login {
    public static User start(Scanner in) {
        System.out.println(Actions.ANSI_LIGHT_GREEN + "Welcome back! Enter your credentials!\n" + Actions.ANSI_RESET);

        Credentials credentials = getCredentials(in);
        while (findUserByCredentials(credentials) == null) {
            System.out.println(Actions.ANSI_LIGHT_RED + "Invalid credentials, please try again!\n" + Actions.ANSI_RESET);
            credentials = getCredentials(in);
        }
        User loggedUser = findUserByCredentials(credentials);
        System.out.println("Welcome back " + Actions.ANSI_LIGHT_GREEN + loggedUser.getName() + Actions.ANSI_RESET + "!");
        System.out.println("Username: " + loggedUser.getUserName());
        if (loggedUser.getUserXP() == 0)
            System.out.println("User experience : - ");
        else if (loggedUser.getUserXP() == Integer.MAX_VALUE)
            System.out.println("User experience : " + Actions.ANSI_BOLD + Actions.ANSI_ORANGE + "∞" + Actions.ANSI_RESET);
        else
            System.out.println("User experience : " + loggedUser.getUserXP());

        return loggedUser;
    }
    public static Credentials getCredentials(Scanner in) {
        String email, password;

        System.out.print("    email: ");
        email = in.nextLine();
        System.out.print("    password: ");
        password = in.nextLine();

        System.out.println();
        return new Credentials(email, password);
    }
    public static User findUserByCredentials(Credentials cred) {
        for (Admin u : IMDB.getInstance().getAdmins())
            if (u.credentialsMatch(cred))
                return u;
        for (Contributor u : IMDB.getInstance().getContributors())
            if (u.credentialsMatch(cred))
                return u;
        for (Regular u : IMDB.getInstance().getRegulars())
            if (u.credentialsMatch(cred))
                return u;
        return null;
    }
}
