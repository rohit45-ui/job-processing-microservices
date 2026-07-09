package com.notification.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.notification.dto.JobCompletedEvent;
import com.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JobCompletedConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "job-completed-topic")
    public void consume(JobCompletedEvent event) {

        System.out.println("Received Completed Job :: " + event);

        notificationService.sendNotification(event);

    }

}