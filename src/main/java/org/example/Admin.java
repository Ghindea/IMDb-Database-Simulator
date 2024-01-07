package org.example;

public class Admin extends Staff implements Observer  {

    Admin(String name, String email, AccountType type) {
        super(name, email, type);
    }
    Admin() {super.setUserType(AccountType.Admin);}
    @Override
    public void update(Observable o) {

    }

}
