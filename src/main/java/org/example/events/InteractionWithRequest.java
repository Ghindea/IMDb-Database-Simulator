package org.example.events;

import org.example.User;

import java.util.ArrayList;
import java.util.List;

public class InteractionWithRequest {
    private List<User> requesters;

    public InteractionWithRequest() {
        this.requesters = new ArrayList<>();
    }
    public void addRequester(User r) {
        requesters.add(r);
    }
    public void removeRequester(User r) {
        requesters.remove(r);
    }
    public void notify(String message) {
        for (User r : requesters)
            r.update(message);
    }

}
