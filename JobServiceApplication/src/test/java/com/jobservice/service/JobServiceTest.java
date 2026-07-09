package com.jobservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jobservice.dto.JobEvent;
import com.jobservice.dto.JobRequest;
import com.jobservice.entity.Job;
import com.jobservice.entity.JobStatus;
import com.jobservice.exception.JobNotFoundException;
import com.jobservice.repository.JobRepository;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest {
	
	@Mock
	private JobRepository repository;
	@Mock
	private KafkaProducerService producer;
	
	@InjectMocks
	private JobService service;
	
	@Test
	void shouldCreateJobSucessfully() {
		JobRequest request = new JobRequest();
		request.setJobName("Generate Report");
		
		Job savedJob = Job.builder()
							.id(1L)
							.jobName("Generate Report")
							.status(JobStatus.PENDING)
							.build();
		
		when(repository.save(any(Job.class))).thenReturn(savedJob);
		
		Job result = service.createJob(request);
		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Generate Report",result.getJobName());
		assertEquals(JobStatus.PENDING,result.getStatus());
		
		verify(repository,times(1)).save(any(Job.class));
		
		ArgumentCaptor<JobEvent> captor = ArgumentCaptor.forClass(JobEvent.class);
		
		verify(producer).publishJobEvent(captor.capture());
		
		JobEvent event = captor.getValue();
		assertEquals(result.getId(), event.getJobId());

		assertEquals(result.getJobName(), event.getJobName());

		assertEquals("PENDING", event.getStatus());
	}
	
	@Test
	void shouldThrowExceptionWhenJobNotFound() {
		when(repository.findById(999L)).thenReturn(Optional.empty());
		assertThrows(JobNotFoundException.class,() -> service.getJob(999L));
	}
}
