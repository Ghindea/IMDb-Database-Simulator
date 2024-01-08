package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class Staff extends User implements StaffInterface{
    private SortedSet<String> contributions;
    private List<Request> requests;
    Staff(String name, String email, AccountType type) {
        super(name, email, type);
    }
    Staff() {
        contributions   = new TreeSet<>();
        requests        = new ArrayList<>();
    }

    public void setContributions(SortedSet<String> contributions) {
        this.contributions = contributions;
    }

    @Override
    public void addProductionSystem(Production p) {
        IMDB.getInstance().getProductions().add(p);
    }

    @Override
    public void addActorSystem(Actor a) {
        IMDB.getInstance().addActor(a);
    }

    @Override
    public void removeProductionSystem(String name) {
        IMDB.getInstance().getProductions().remove(name);
    }

    @Override
    public void removeActorSystem(String name) {
        IMDB.getInstance().getActors().remove(name);
    }

    @Override
    public void updateProduction(Production p) {
        Production original = IMDB.getInstance().getProduction(p.getTitle());
        int index = IMDB.getInstance().getProductions().indexOf(original);
        IMDB.getInstance().getProductions().set(index, p);
    }

    @Override
    public void updateActor(Actor a) {
        Actor original = IMDB.getInstance().getActor(a.getName());
        int index = IMDB.getInstance().getActors().indexOf(original);
        IMDB.getInstance().getActors().set(index, a);
    }

    public SortedSet<String> getContributions() {
        return contributions;
    }
    @Override
    public int compareTo(@NotNull Object o) {
        return super.getUserName().compareTo(((Staff) o).getUserName());
    }
}
