package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class Credentials {
    private static final int PASSWORD_LENGTH = 16;
    private String email, password;

    public Credentials(String email) {
        this.email = email;
        this.password = generateRandomPassword(); // auto generate random password
    }
    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public static String generateRandomPassword() {
        String password = new String();
        for (int i = 0; i < PASSWORD_LENGTH; i++)
            password += (char) ThreadLocalRandom.current().nextInt(33, 127);
        return password;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public Boolean equals(Credentials c) {
        return this.email.equals(c.getEmail()) && this.password.equals(c.getPassword());
    }
}
