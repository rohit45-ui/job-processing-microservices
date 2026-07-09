package com.jobservice.service;

import org.springframework.stereotype.Service;

import com.jobservice.dto.JobEvent;
import com.jobservice.dto.JobRequest;
import com.jobservice.entity.Job;
import com.jobservice.entity.JobStatus;
import com.jobservice.exception.JobNotFoundException;
import com.jobservice.repository.JobRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobService {
	
	private final JobRepository repository;
	
	private final KafkaProducerService producer;
	
	
	public Job createJob(JobRequest request) {
		
		Job job = Job.builder()
					.jobName(request.getJobName())
					.status(JobStatus.PENDING)
					.build();		
		
		Job savedJob= repository.save(job);
		
		JobEvent event = JobEvent
							.builder()
							.jobId(savedJob.getId())
							.jobName(savedJob.getJobName())
							.status(savedJob.getStatus().name())
							.build();
		
		
		producer.publishJobEvent(event);
		
		return savedJob;
	}
	
	
	
	public Job getJob(long id) {
		return repository.findById(id)
		          .orElseThrow(() ->
	              new JobNotFoundException(
	                  "Job not found with id : " + id));
	}
	
	@Transactional
	public void updateStatus(Long id, JobStatus status) {
		Job job = repository.findById(id).orElseThrow(() -> new JobNotFoundException("Job Not Found With Id :: "+id));
		job.setStatus(status);
		repository.save(job);
	}
}
