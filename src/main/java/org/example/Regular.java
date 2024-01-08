package org.example;

import org.jetbrains.annotations.NotNull;

public class Regular extends User implements RequestManager{
    Regular(String name, String email, AccountType type) {
        super(name, email, type);
    }
    Regular(){super.setUserType(AccountType.Regular);}
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
                if (r.getToUser().equals("Staff") && u instanceof Staff)
                    r.addObserver(u);
            }
        }
        RequestHolder.pushRequest(r);
    }
    @Override
    public void removeRequest(Request r) {
        RequestHolder.pullRequest(r);
    }
    public void addRating(Production p, Rating r) {
        int index = IMDB.getInstance().getProductions().indexOf(p);
        p.addRating(r);
        p.notify(p.getType() + " " + p.getTitle() + " received a new rating from user " +
                this.getUserName() + " -> " + Actions.ANSI_YELLOW + r.getRating() +"★" + Actions.ANSI_RESET);
        p.addObserver(this);
        IMDB.getInstance().getProductions().set(index,p);
    }
    public Boolean deleteRating(Production p) {
        for (Rating r : p.getRatings())
            if (r.getUserName().equals(this.getUserName())) {
                p.getRatings().remove(r);
                return true;
            }
        return false;
    }
    @Override
    public int compareTo(@NotNull Object o) {
        return super.getUserName().compareTo(((Regular) o).getUserName());
    }
}
