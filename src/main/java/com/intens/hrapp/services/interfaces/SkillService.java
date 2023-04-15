package com.intens.hrapp.services.interfaces;

import com.intens.hrapp.dto.SkillDTO;
import com.intens.hrapp.models.Skill;
import org.springframework.data.domain.Page;

public interface SkillService {

    Skill createSkill(Skill skill);
    Skill updateSkill(Long id, SkillDTO skillDTO);
    Skill getSkillById(Long id);
    Page<Skill> getAllSkills(Long page);
    void deleteSkillById(Long id);

}
