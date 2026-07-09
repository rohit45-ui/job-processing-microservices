package com.jobservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.jobservice.dto.JobEvent;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {
	

	private  final KafkaTemplate<String, JobEvent> kafkaTemplate;
	
	
	@Value("${app.tpc.name}")
	private String topicName;
	
	public void publishJobEvent(JobEvent event) {
		kafkaTemplate.send(topicName, event);
		log.info("Published Job {} to topic {}", event.getJobId(), topicName);
	}
	
	
}
