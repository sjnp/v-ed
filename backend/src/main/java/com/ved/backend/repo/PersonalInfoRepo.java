package com.ved.backend.repo;

import com.ved.backend.model.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalInfoRepo extends JpaRepository<PersonalInfo, Long> {
}
