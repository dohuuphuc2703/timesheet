package com.example.timesheet.configuration;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.timesheet.service.EmailSentEvent;

@Component
public class EmailEventListener {

    @EventListener
    public void handleEmailSentEvent(EmailSentEvent event) {
        System.out.println("Email sent to: " + event.getEmail());
    }
}
