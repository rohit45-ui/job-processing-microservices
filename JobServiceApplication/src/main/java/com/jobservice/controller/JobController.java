package com.jobservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobservice.dto.JobRequest;
import com.jobservice.entity.Job;
import com.jobservice.entity.JobStatus;
import com.jobservice.service.JobService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {
	
	private final JobService service;
	
	@PostMapping
	public Job createJob(@Valid @RequestBody JobRequest request) {
		return service.createJob(request);
	}
	
	@GetMapping("/{id}")
	public Job getJob(@PathVariable Long id) {
		return service.getJob(id);
	}
	
	
	@PatchMapping("/{id}/status")
	public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam JobStatus status){
		service.updateStatus(id,status);
		return ResponseEntity.ok("Status Updated");
	}
}
