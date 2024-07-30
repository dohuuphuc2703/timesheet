package com.example.timesheet.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.example.timesheet.entity.User;
import com.example.timesheet.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SchedulerService {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private UserRepository userRepository;

    private ScheduledFuture<?> scheduledFuture;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private String cronExpression = "0 0 * * * *"; // Default cron expression

    @PostConstruct
    public void startScheduler() {
        // Schedule the task with the initial cron expression
        scheduledFuture = taskScheduler.schedule(this::executeTask, new CronTrigger(cronExpression));
    }

    public void updateCronExpression(String newCronExpression) {
        this.cronExpression = newCronExpression;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false); // Cancel the existing scheduled task
        }
        startScheduler(); // Reschedule with the new cron expression
    }

    @Async
    public void sendEmail(String to, String subject, String text) {
        log.info("Sending email to ");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        // Phát sự kiện sau khi gửi email thành công
        eventPublisher.publishEvent(new EmailSentEvent(this, to));
    }

    private void executeTask() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            sendEmail(user.getUsername(), "Opentalk", "Email body content");
            System.out.println("Send at " + new Date());
        }
        // Your task logic here

    }
}
