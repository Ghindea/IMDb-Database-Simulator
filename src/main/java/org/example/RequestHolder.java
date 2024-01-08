package org.example;

import java.util.ArrayList;
import java.util.List;

public class RequestHolder {
    public static List<Request> requestsForAdmins = new ArrayList<>();
    public static void pushRequest(Request r) {
        requestsForAdmins.add(r);
    }
    public static void pullRequest(Request r) {
        requestsForAdmins.remove(r);
    }
}
