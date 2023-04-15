package com.intens.hrapp.services.interfaces;

import com.intens.hrapp.dto.CandidateDTO;
import com.intens.hrapp.models.Candidate;
import com.intens.hrapp.models.Skill;
import com.intens.hrapp.repositories.CandidateRepository;
import com.intens.hrapp.services.CandidateServiceImpl;
import com.intens.hrapp.services.SkillServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private SkillServiceImpl skillService;
    @InjectMocks
    private CandidateServiceImpl candidateService;
    private final List<Candidate> candidates = new ArrayList<>();
    private final List<Skill> skills = new ArrayList<>();
    
    @BeforeEach
    public void setUp(){
        Skill skill1 = new Skill(1L, "java", null);
        Skill skill2 = new Skill(2L, "C#", null);
        Skill skill3 = new Skill(3L, "english language", null);
        Candidate candidate1 = new Candidate("123", "name 1", Date.valueOf("1999-10-10"), "123456789", "microsoft@gmail.com", new HashSet<>());
        Candidate candidate2 = new Candidate("456", "name 2", Date.valueOf("2030-03-05"), "987654321", "ubisoft@gmail.com", new HashSet<>());
        Candidate candidate3 = new Candidate("789", "name 3", Date.valueOf("2024-10-11"), "654321987", "some@gmail.com", new HashSet<>());
        candidate1.getSkills().add(skill1);
        candidate2.getSkills().addAll(Set.of(skill1, skill2));
        candidate3.getSkills().addAll(Set.of(skill1, skill2, skill3));
        candidates.addAll(List.of(candidate1, candidate2, candidate3));
        skills.addAll(List.of(skill1, skill2, skill3));
    }

    @Test
    void createCandidate() {
        Mockito.when(candidateRepository.save(candidates.get(0))).thenReturn(candidates.get(0));
        assertThat(candidateService.createCandidate(candidates.get(0))).isEqualTo(candidates.get(0));
    }

    @Test
    void updateCandidateDetails() {
        Candidate updatedCandidate = candidates.get(0);
        CandidateDTO updates = new CandidateDTO();
        updates.setName("name 4");
        updatedCandidate.setName("name 4");
        Mockito.when(candidateRepository.findById("123")).thenReturn(Optional.of(candidates.get(0)));
        Mockito.when(candidateRepository.save(updatedCandidate)).thenReturn(updatedCandidate);
        assertThat(candidateService.updateCandidateDetails("123", updates)).isEqualTo(updatedCandidate);
    }

    @Test
    void getCandidateById() {
        Mockito.when(candidateRepository.findById("123")).thenReturn(Optional.of(candidates.get(0)));
        assertThat(candidateService.getCandidateById("123")).isEqualTo(candidates.get(0));
    }

    @Test
    void getAllCandidates() {
        Mockito.when(candidateRepository.findAll(PageRequest.of(0, 5))).thenReturn(new PageImpl<>(candidates));
        assertThat(candidateService.getAllCandidates(0L)).isEqualTo(new PageImpl<>(candidates));
    }

    @Test
    void deleteCandidateById() {
        Mockito.doNothing().when(candidateRepository).delete(candidates.get(0));
        Mockito.when(candidateRepository.findById("123")).thenReturn(Optional.of(candidates.get(0)));
        candidateService.deleteCandidateById("123");
        Mockito.verify(candidateRepository, Mockito.times(1)).delete(candidates.get(0));
    }

    @Test
    void addSkill() {
        Mockito.when(candidateRepository.findById("123")).thenReturn(Optional.of(candidates.get(0)));
        Mockito.when(skillService.getSkillById(2L)).thenReturn(skills.get(1));
        Candidate candidateWithNewSkill = candidates.get(0);
        candidateWithNewSkill.getSkills().add(skills.get(1));
        Mockito.when(candidateRepository.save(candidateWithNewSkill)).thenReturn(candidateWithNewSkill);
        assertThat(candidateService.addSkill("123", 2L)).isEqualTo(candidateWithNewSkill);
    }

    @Test
    void removeSkill() {
        Mockito.when(candidateRepository.findById("123")).thenReturn(Optional.of(candidates.get(0)));
        Mockito.when(skillService.getSkillById(2L)).thenReturn(skills.get(1));
        Candidate candidateWithSkillRemoved = candidates.get(0);
        candidateWithSkillRemoved.getSkills().remove(skills.get(0));
        Mockito.when(candidateRepository.save(candidateWithSkillRemoved)).thenReturn(candidateWithSkillRemoved);
        assertThat(candidateService.addSkill("123", 2L)).isEqualTo(candidateWithSkillRemoved);
    }

    @Test
    void getCandidatesByName() {
        Mockito.when(candidateRepository.findAllByNameIgnoreCase(PageRequest.of(0, 5), "name 1")).thenReturn(new PageImpl<>(List.of(candidates.get(0))));
        assertThat(candidateService.getCandidatesByName("name 1", 0L)).isEqualTo(new PageImpl<>(candidates.subList(0, 1)));
    }

    @Test
    void getCandidatesBySkills() {
        Mockito.when(candidateRepository.findAll()).thenReturn(candidates);
        for(long i = 0; i < skills.size(); i++){
            Mockito.when(skillService.getSkillById(i + 1)).thenReturn(skills.get((int) i));
        }
        assertThat(candidateService.getCandidatesBySkills(List.of(1L, 2L), 0L).getContent()).isEqualTo(new PageImpl<>(candidates.subList(1,3)).getContent());
    }
}