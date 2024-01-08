package org.example;

public class Contributor extends Staff implements RequestManager{
    Contributor(String name, String email, AccountType type) {
        super(name, email, type);
    }
    Contributor(){super.setUserType(AccountType.Contributor);}
    @Override
    public void createRequest(Request r) {
        r.addObserver(this);
        if (r.getToUser().equalsIgnoreCase("ADMIN")) {
            for (Admin a : IMDB.getInstance().getAdmins()) {
                r.addObserver(a);
            }
        } else {
            for (User u : IMDB.getInstance().getUsers()) {
                if (u.getUserName().equals(r.getToUser()))
                    r.addObserver(u);
            }
        }
        RequestHolder.pushRequest(r);
    }
    @Override
    public void removeRequest(Request r) {
        RequestHolder.pullRequest(r);
    }
}
