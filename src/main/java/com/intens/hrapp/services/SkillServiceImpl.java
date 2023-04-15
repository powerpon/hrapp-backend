package com.intens.hrapp.services;

import com.intens.hrapp.dto.SkillDTO;
import com.intens.hrapp.exceptions.SkillNotFoundException;
import com.intens.hrapp.models.Candidate;
import com.intens.hrapp.models.Skill;
import com.intens.hrapp.repositories.SkillRepository;
import com.intens.hrapp.services.interfaces.SkillService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private static final int SKILLS_PER_PAGE_RETURNED = 5;

    @Autowired
    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    public Skill updateSkill(Long id, SkillDTO skillDTO) {
        Skill skillToUpdate = this.getSkillById(id);
        Optional.ofNullable(skillDTO.getName()).ifPresent(skillToUpdate::setName);
        return skillRepository.save(skillToUpdate);
    }

    @Override
    public Skill getSkillById(Long id) {
        return skillRepository.findById(id).orElseThrow(SkillNotFoundException::new);
    }

    @Override
    public Page<Skill> getAllSkills(Long page) {
        return skillRepository.findAll(PageRequest.of(Math.toIntExact(page), SkillServiceImpl.SKILLS_PER_PAGE_RETURNED));
    }

    @Override
    public void deleteSkillById(Long id) {
        Skill skillToDelete = this.getSkillById(id);
        if(skillToDelete.getCandidates() != null) {
            for (Candidate candidate : skillToDelete.getCandidates()) {
                candidate.getSkills().remove(skillToDelete);
            }
        }
        skillRepository.delete(skillToDelete);
    }

}
