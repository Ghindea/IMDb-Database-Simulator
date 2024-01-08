package org.example;

public class Admin extends Staff  {

    Admin(String name, String email, AccountType type) {
        super(name, email, type);
    }
    Admin() {super.setUserType(AccountType.Admin);}

}
