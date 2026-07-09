package com.worker.service;

import org.springframework.stereotype.Service;

import com.worker.dto.JobCompletedEvent;
import com.worker.dto.JobEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobProcessorService {    
	
	private  final JobStatusService statusService;
	private final KafkaProducerService producerService;
	
//	public void processJob(JobEvent event) {
//		try {
//			statusService.updateStatus(event.getJobId(), "PROCESSING");
//			log.info("Processing : {}",event.getJobName());
//			Thread.sleep(5000);
//			statusService.updateStatus(event.getJobId(), "COMPLETED");
//			log.info("Completed : {}",event.getJobName());
//			
//			
//			JobCompletedEvent completedEvent =
//			        new JobCompletedEvent(
//			                event.getJobId(),
//			                event.getJobName(),
//			                "COMPLETED");
//
//			producerService.publishCompletedJob(completedEvent);
//			
//			log.info("Completed : {}", event.getJobName());
//			
//		}
//		catch(Exception e) {
//			statusService.updateStatus(event.getJobId(), "FAILED");
//			log.error("Error while processing job",e);
//		}
//	}
	
	public void processJob(JobEvent event) {

	    try {

	        System.out.println("========== STEP 1 ==========");
	        statusService.updateStatus(event.getJobId(), "PROCESSING");

	        System.out.println("========== STEP 2 ==========");
	        log.info("Processing : {}", event.getJobName());

	        Thread.sleep(5000);

	        System.out.println("========== STEP 3 ==========");
	        statusService.updateStatus(event.getJobId(), "COMPLETED");

	        JobCompletedEvent completedEvent =
	                new JobCompletedEvent(
	                        event.getJobId(),
	                        event.getJobName(),
	                        "COMPLETED");

	        System.out.println("========== STEP 4 ==========");
	        producerService.publishCompletedJob(completedEvent);

	        System.out.println("========== STEP 5 ==========");

	    } catch (Exception e) {

	        System.out.println("========== EXCEPTION ==========");
	        e.printStackTrace();

	        log.error("Error while processing job", e);

	        try {
	            statusService.updateStatus(event.getJobId(), "FAILED");
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	}
}
