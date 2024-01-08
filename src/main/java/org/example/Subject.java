package org.example;

public interface Subject {
    public void notifyObservers();
    public void registerObserver(Observer o);
    public void unregisterObserver(Observer o);
}
