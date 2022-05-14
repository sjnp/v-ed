package com.ved.backend.repo;

import com.ved.backend.model.Instructor;
import com.ved.backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

public interface InstructorRepo extends JpaRepository<Instructor, Long> {

      Optional<Instructor> findByStudent(Student student);

}
