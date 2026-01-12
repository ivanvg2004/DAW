package com.liceu.hibernate.repository;

import com.liceu.hibernate.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepo extends JpaRepository<Job, Long> {

}
