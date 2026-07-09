package com.worker.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobStatusService {
	
	private final RestClient.Builder builder;
	
	
	
	public void updateStatus(Long id, String status) {
		builder.build()
        .patch()
        .uri("http://JOBSERVICE/jobs/{id}/status?status={status}", id, status)
        .retrieve()
        .toBodilessEntity();

			System.out.println("Status Updated : " + status);
	}
}
