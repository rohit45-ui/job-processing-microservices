package com.notification.service;

import org.springframework.stereotype.Service;

import com.notification.dto.JobCompletedEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationService {

    public void sendNotification(JobCompletedEvent event) {

    	
        log.info("======================================");
        log.info("EMAIL SENT SUCCESSFULLY");
        log.info("Job Id   : {}" , event.getJobId());
        log.info("Job Name : {}" , event.getJobName());
        log.info("Status   : {}" , event.getStatus());
        log.info("======================================");
        

    }

}
