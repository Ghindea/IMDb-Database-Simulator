package org.example;

import java.time.LocalDateTime;

public class Request extends Observable{
    private RequestType type;
    private LocalDateTime requestDate;
    private String title = null, description, fromUserName, toUser;


    public Request setType(RequestType type) {
        this.type = type;
        return this;
    }
    public Request setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
        return this;
    }
    public Request setTitle(String title) {
        this.title = title;
        return this;
    }
    public Request setDescription(String description) {
        this.description = description;
        return this;
    }
    public Request setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
        return this;
    }
    public Request setToUser(String toUser) {
        this.toUser = toUser;
        return this;
    }

    public RequestType getType() {
        return type;
    }
    public LocalDateTime getRequestDate() {
        return requestDate;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getFromUserName() {
        return fromUserName;
    }
    public String getToUser() {
        return toUser;
    }

    public String displayInfo(int index) {
        if (this.getTitle() != null) {
            String message = "\t↪ " + index + ") " + Actions.ANSI_BRIGHT_CYAN + this.getType() + Actions.ANSI_RESET +
                    " from " + this.getFromUserName() +
                    " : " + this.getTitle() + " ⇒ \"" + Actions.ANSI_LIGHT_GREEN + this.getDescription() + Actions.ANSI_RESET +"\" at " + this.getRequestDate();
            System.out.print(message);
            return message;
        }
        else {
            String message = "\t↪ " + index + ") " + Actions.ANSI_BRIGHT_CYAN + this.getType() +Actions.ANSI_RESET
                    + " from " + this.getFromUserName() +
                    " ⇒ \"" + Actions.ANSI_LIGHT_GREEN + this.getDescription() + Actions.ANSI_RESET + "\" at " + this.getRequestDate();
            System.out.print(message);
            return message;
        }
    }
}
