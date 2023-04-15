package com.intens.hrapp.services.interfaces;

import com.intens.hrapp.dto.SkillDTO;
import com.intens.hrapp.models.Skill;
import com.intens.hrapp.repositories.SkillRepository;
import com.intens.hrapp.services.SkillServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SkillServiceTest {

    @Mock
    private SkillRepository skillRepository;
    @InjectMocks
    private SkillServiceImpl skillService;
    private final List<Skill> skills = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        Skill skill1 = new Skill(1L, "java", null);
        Skill skill2 = new Skill(2L, "C#", null);
        Skill skill3 = new Skill(3L, "english language", null);
        skills.addAll(List.of(skill1, skill2, skill3));
    }

    @Test
    void createSkill() {
        Mockito.when(skillRepository.save(skills.get(0))).thenReturn(skills.get(0));
        assertThat(skillService.createSkill(skills.get(0))).isEqualTo(skills.get(0));
    }

    @Test
    void updateSkill() {
        Mockito.when(skillRepository.findById(1L)).thenReturn(Optional.of(skills.get(0)));
        Skill updatedSkill = skills.get(0);
        updatedSkill.setName("python");
        SkillDTO updates = new SkillDTO();
        updates.setName("python");
        Mockito.when(skillRepository.save(updatedSkill)).thenReturn(updatedSkill);
        assertThat(skillService.updateSkill(1L, updates)).isEqualTo(updatedSkill);
    }

    @Test
    void getSkillById() {
        Mockito.when(skillRepository.findById(1L)).thenReturn(Optional.of(skills.get(0)));
        assertThat(skillService.getSkillById(1L)).isEqualTo(skills.get(0));
    }

    @Test
    void getAllSkills() {
        Mockito.when(skillRepository.findAll(PageRequest.of(0, 5))).thenReturn(new PageImpl<>(skills));
        assertThat(skillService.getAllSkills(0L)).isEqualTo(new PageImpl<>(skills));
    }

    @Test
    void deleteSkillById() {
        Mockito.doNothing().when(skillRepository).delete(skills.get(0));
        Mockito.when(skillRepository.findById(1L)).thenReturn(Optional.of(skills.get(0)));
        skillService.deleteSkillById(1L);
        Mockito.verify(skillRepository, Mockito.times(1)).delete(skills.get(0));
    }

}