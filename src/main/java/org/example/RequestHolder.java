package org.example;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RequestHolder {
    public static List<Request> requestsForAdmins = new ArrayList<>();
    public static List<Request> globalRequests = new ArrayList<>();
    public static void pushRequest(Request r) {
        if (r.getToUser().equalsIgnoreCase("ADMIN"))
            requestsForAdmins.add(r);
        else
            globalRequests.add(r);

        r.notify(r.getFromUserName() + " pushed a new request at " + r.getRequestDate().format(Parser.dateTimeFormat) + " ⇒ " +
                Actions.ANSI_LIGHT_GREEN + r.getDescription() + Actions.ANSI_RESET);
    }
    public static void pullRequest(Request r) {
        if (r.getToUser().equalsIgnoreCase("ADMIN"))
            requestsForAdmins.remove(r);
        else
            globalRequests.remove(r);
        r.notify("User " + r.getFromUserName() + " pulled a request.");
    }
}
