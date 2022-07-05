package com.ved.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ved.backend.model.ReportState;

public interface ReportStateRepo extends JpaRepository<ReportState, Long>{
    
    ReportState findByName(String name);

}