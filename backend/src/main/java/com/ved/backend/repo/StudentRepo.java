package com.ved.backend.repo;

import com.ved.backend.model.AppUser;
import com.ved.backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Long> {

    //  Student findByAppUserId(Long id);

    //  Student getByAppUserId(Long id);

  Optional<Student> findByAppUser(AppUser appUser);

}