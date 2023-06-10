package com.bestudent.repository;

import com.bestudent.domain.Study;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface StudyRepository extends Repository<Study,Long> {

    Study save(Study study);

    Optional<Study> findById(Long id);
}
