package com.intens.hrapp.repositories;

import com.intens.hrapp.models.Skill;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    @NonNull Page<Skill> findAll(@NonNull Pageable page);

}
