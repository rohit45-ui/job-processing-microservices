package com.jobservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobservice.dto.JobRequest;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class JobApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldCreateJobUsingRestApi() throws Exception {

        JobRequest request = new JobRequest();
        request.setJobName("API Integration Test");

        mockMvc.perform(post("/jobs")
                .contentType("application/json")
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jobName")
                        .value("API Integration Test"))
                .andExpect(jsonPath("$.status")
                        .value("PENDING"));
    }
    
    @Test
    void shouldReturnBadRequestWhenJobNameIsBlank() throws Exception {

        JobRequest request = new JobRequest();
        request.setJobName("");

        mockMvc.perform(post("/jobs")
                .contentType("application/json")
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value("Validation Failed"))
                .andExpect(jsonPath("$.errors.jobName")
                        .value("Job Name is required"));
    }
    
    @Test
    void shouldReturnNotFoundWhenJobDoesNotExist() throws Exception {

        mockMvc.perform(get("/jobs/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Job not found with id : 99999"));
    }
}
