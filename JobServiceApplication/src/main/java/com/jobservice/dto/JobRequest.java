package com.jobservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JobRequest {
	@NotBlank(message = "Job Name is required")
	@Size(min = 3 ,max = 100,message = "Job Name Must be in between 3 to 100 characters")
	private String jobName;
}
