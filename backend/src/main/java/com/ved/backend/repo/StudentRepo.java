package com.ved.backend.repo;

import com.ved.backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {

    //  Student findByAppUserId(Long id);

    //  Student getByAppUserId(Long id);

}