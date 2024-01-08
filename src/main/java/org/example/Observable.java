package org.example;

import java.util.ArrayList;
import java.util.Vector;

public class Observable {
    private ArrayList<User> observers;
    Observable() {observers = new ArrayList<>();}

    public void addObserver(User u) {
        if (!observers.contains(u))
            observers.add(u);
    }
    public void removeObserver(User u) {
        observers.remove(u);
    }

    public ArrayList<User> getObservers() {
        return observers;
    }

    public void notify(String message) {
        observers.forEach(observer -> observer.update(message));
    }
    public void notify(User u, String message) {
        u.update(message);
    }
    // TODO finish this class accordind to the task
}
