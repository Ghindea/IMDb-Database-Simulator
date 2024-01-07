package org.example;

import java.util.Vector;

public class Observable {
    private Vector observers = new Vector();
    private Boolean changed = false;
    public void addObserver(Observer o) {
        if (!observers.contains(o))
            observers.add(o);
    }
    public void removeObserver(Observable o) {
        observers.remove(o);
    }
    protected void setChanged() {
        changed = true;
    }
    public void notifyObservers() {
        if (!changed) return;
        for (int i = 0; i < observers.size()-1; i++)
            ((Observer)observers.elementAt(i)).update(this);
    }
    // TODO finish this class accordind to the task
}
