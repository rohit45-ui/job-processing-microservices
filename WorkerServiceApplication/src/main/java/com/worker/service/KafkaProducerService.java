package com.worker.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.worker.dto.JobCompletedEvent;
import com.worker.dto.JobEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCompletedJob(JobCompletedEvent event) {

        kafkaTemplate.send("job-completed-topic", event);

        System.out.println("-----------------------------------");
        System.out.println("Published Completed Event");
        System.out.println(event);
        System.out.println("-----------------------------------");

    }
    
    public void publishJobEvent(JobEvent event) {
        kafkaTemplate.send("job-event-topic-v2", event);
    }

}
