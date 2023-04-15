package com.intens.hrapp.repositories;

import com.intens.hrapp.models.Candidate;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, String> {

    @NonNull Page<Candidate> findAll(@NonNull Pageable page);
    Page<Candidate> findAllByNameIgnoreCase(Pageable page, String name);

}
