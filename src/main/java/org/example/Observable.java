package org.example;

import java.util.ArrayList;
import java.util.Vector;

public class Observable {
    private ArrayList<User> observers;

    public void addObserver(User u) {
        observers.add(u);
    }
    public void removeObserver(User u) {
        observers.remove(u);
    }
    public void notify(String message) {
        observers.forEach(observer -> observer.update(message));
    }
    // TODO finish this class accordind to the task
}
