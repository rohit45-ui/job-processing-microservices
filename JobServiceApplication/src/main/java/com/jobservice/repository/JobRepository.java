package com.jobservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobservice.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long>{

}
