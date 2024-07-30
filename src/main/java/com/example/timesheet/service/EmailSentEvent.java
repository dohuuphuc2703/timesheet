package com.example.timesheet.service;

import org.springframework.context.ApplicationEvent;

public class EmailSentEvent extends ApplicationEvent {

    private final String email;

    public EmailSentEvent(Object source, String email) {
        super(source);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
