package com.ved.backend.repo;

import com.ved.backend.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepo extends JpaRepository<Instructor, Long> {
}
