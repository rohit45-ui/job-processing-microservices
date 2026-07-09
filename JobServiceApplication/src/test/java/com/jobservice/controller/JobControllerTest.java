package com.jobservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobservice.dto.JobRequest;
import com.jobservice.entity.Job;
import com.jobservice.entity.JobStatus;
import com.jobservice.exception.JobNotFoundException;
import com.jobservice.service.JobService;

@WebMvcTest(JobController.class)
public class JobControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockitoBean
	private JobService service;
	
	@Test
	void shouldCreateJob() throws Exception{
		JobRequest request = new JobRequest();
		request.setJobName("Spring Boot Testing");
		
		Job job = Job.builder()
		        .id(1L)
		        .jobName("Spring Boot Testing")
		        .status(JobStatus.PENDING)
		        .build();

		when(service.createJob(any(JobRequest.class)))
		        .thenReturn(job);
	
		
		mockMvc.perform(
	            post("/jobs")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(mapper.writeValueAsString(request))
	    )
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.id").value(1))
	            .andExpect(jsonPath("$.jobName")
	                    .value("Spring Boot Testing"))
	            .andExpect(jsonPath("$.status")
	                    .value("PENDING"));
	}
	
	
	@Test
	void shouldReturnBadRequestWhenJobNameIsInvalid() throws Exception {

	    JobRequest request = new JobRequest();
	    request.setJobName("");

	    mockMvc.perform(post("/jobs")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(mapper.writeValueAsString(request)))
	            .andExpect(status().isBadRequest());

	    verify(service, never()).createJob(any(JobRequest.class));
	}
	
	@Test
	void shouldReturnNotFoundWhenJobDoesNotExist() throws Exception {

	    when(service.getJob(999L))
	            .thenThrow(new JobNotFoundException("Job not found with id : 999"));

	    mockMvc.perform(get("/jobs/999"))
	            .andExpect(status().isNotFound())
	            .andExpect(jsonPath("$.message")
	                    .value("Job not found with id : 999"));
	}
	
}
