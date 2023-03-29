package com.ey.gds.solutionarchitect.auth0security.dto;

public class Message {

    private final String message;

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
