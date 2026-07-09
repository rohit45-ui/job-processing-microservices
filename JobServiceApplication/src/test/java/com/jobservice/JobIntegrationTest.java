package com.jobservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.jobservice.dto.JobRequest;
import com.jobservice.entity.Job;
import com.jobservice.entity.JobStatus;
import com.jobservice.repository.JobRepository;
import com.jobservice.service.JobService;

@SpringBootTest
@ActiveProfiles("test")
class JobIntegrationTest {

    @Autowired
    private JobService jobService;
    
    @Autowired
    private JobRepository repository;

    @Test
    void shouldCreateJobSuccessfully() {

        JobRequest request = new JobRequest();
        request.setJobName("Integration Test Job");

        Job job = jobService.createJob(request);

        assertEquals("Integration Test Job", job.getJobName());
        assertEquals(JobStatus.PENDING, job.getStatus());

        Job savedJob = repository.findById(job.getId()).orElse(null);

        assertNotNull(savedJob);
        assertEquals("Integration Test Job", savedJob.getJobName());
    }
}