package com.worker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobCompletedEvent {

    private Long jobId;
    private String jobName;
    private String status;

}
