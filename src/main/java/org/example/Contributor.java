package org.example;

public class Contributor extends Staff implements RequestManager{
    Contributor(String name, String email, AccountType type) {
        super(name, email, type);
    }
    Contributor(){super.setUserType(AccountType.Contributor);}
    @Override
    public void createRequest(Request r) {

    }

    @Override
    public void removeRequest(Request r) {

    }
    // TODO this
}
