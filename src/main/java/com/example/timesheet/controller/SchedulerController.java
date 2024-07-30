package com.example.timesheet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.timesheet.service.SchedulerService;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;

    @PostMapping("/updateCron")
    public String updateCronExpression(@RequestParam String cronExpression) {
        schedulerService.updateCronExpression(cronExpression);
        return "success";
    }
}
