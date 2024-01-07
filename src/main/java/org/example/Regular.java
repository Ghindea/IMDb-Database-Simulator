package org.example;

import org.jetbrains.annotations.NotNull;

public class Regular extends User implements RequestManager, Observer{
    Regular(String name, String email, AccountType type) {
        super(name, email, type);
    }
    Regular(){super.setUserType(AccountType.Regular);}
    @Override
    public void createRequest(Request r) {

    }
    @Override
    public void removeRequest(Request r) {

    }
    @Override
    public void update(Observable o) {

    }

    @Override
    public int compareTo(@NotNull Object o) {
        return super.getUserName().compareTo(((Regular) o).getUserName());
    }
}
