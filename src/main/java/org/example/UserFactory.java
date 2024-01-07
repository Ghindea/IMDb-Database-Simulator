package org.example;

public class UserFactory {
    public static User buildUser(AccountType type) {
        switch (type) {
            case Admin -> {
                return new Admin();
            }
            case Regular -> {
                return new Regular();
            }
            case Contributor -> {
                return new Contributor();
            }
            default -> {
                return null;
            }
        }
    }
}
