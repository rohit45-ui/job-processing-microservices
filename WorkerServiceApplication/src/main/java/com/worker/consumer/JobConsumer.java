package com.worker.consumer;

import java.util.concurrent.ExecutorService;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.worker.dto.JobEvent;
import com.worker.service.JobProcessorService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JobConsumer {

    private final ExecutorService executorService;
	private final JobProcessorService processorService;


	@KafkaListener(
	        topics = "job-event-topic-v2",
	        groupId = "worker-group",
	        containerFactory = "kafkaListenerContainerFactory")
	public void consume(JobEvent event) {

	    System.out.println("Received Job :: " + event);

	    processorService.processJob(event);
	}
}
