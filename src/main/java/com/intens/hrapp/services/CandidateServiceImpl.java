package com.intens.hrapp.services;

import com.intens.hrapp.dto.CandidateDTO;
import com.intens.hrapp.exceptions.CandidateNotFoundException;
import com.intens.hrapp.models.Candidate;
import com.intens.hrapp.models.Skill;
import com.intens.hrapp.repositories.CandidateRepository;
import com.intens.hrapp.services.interfaces.CandidateService;
import com.intens.hrapp.services.interfaces.SkillService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final SkillService skillService;
    private static final int CANDIDATES_PER_PAGE_RETURNED = 5;

    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository, SkillService skillService) {
        this.candidateRepository = candidateRepository;
        this.skillService = skillService;
    }

    @Override
    public Candidate createCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate updateCandidateDetails(String id, CandidateDTO candidateDTO) {
        Candidate candidateToUpdate = this.getCandidateById(id);
        Optional.ofNullable(candidateDTO.getName()).ifPresent(candidateToUpdate::setName);
        Optional.ofNullable(candidateDTO.getEmail()).ifPresent(candidateToUpdate::setEmail);
        Optional.ofNullable(candidateDTO.getContactNumber()).ifPresent(candidateToUpdate::setContactNumber);
        Optional.ofNullable(candidateDTO.getDateOfBirth()).ifPresent(candidateToUpdate::setDateOfBirth);
        return candidateRepository.save(candidateToUpdate);
    }

    @Override
    public Candidate getCandidateById(String id) {
        return candidateRepository.findById(id).orElseThrow(CandidateNotFoundException::new);
    }

    @Override
    public Page<Candidate> getAllCandidates(Long page) {
        return candidateRepository.findAll(PageRequest.of(Math.toIntExact(page), CandidateServiceImpl.CANDIDATES_PER_PAGE_RETURNED));
    }

    @Override
    public void deleteCandidateById(String id) {
        Candidate candidateToDelete = this.getCandidateById(id);
        candidateRepository.delete(candidateToDelete);
    }

    @Override
    public Candidate addSkill(String candidateId, Long skillId) {
        Candidate candidateToAddTo = this.getCandidateById(candidateId);
        candidateToAddTo.getSkills().add(skillService.getSkillById(skillId));
        return candidateRepository.save(candidateToAddTo);
    }

    @Override
    public Candidate removeSkill(String candidateId, Long skillId) {
        Candidate candidateToRemoveFrom = this.getCandidateById(candidateId);
        candidateToRemoveFrom.getSkills().remove(skillService.getSkillById(skillId));
        return candidateRepository.save(candidateToRemoveFrom);
    }

    @Override
    public Page<Candidate> getCandidatesByName(String name, Long page) {
        return candidateRepository.findAllByNameIgnoreCase(PageRequest.of(Math.toIntExact(page), CandidateServiceImpl.CANDIDATES_PER_PAGE_RETURNED), name);
    }

    @Override
    public Page<Candidate> getCandidatesBySkills(List<Long> skillIds, Long page) {
        List<Candidate> candidates = candidateRepository.findAll();
        List<Skill> skills = skillIds.stream().map(skillService::getSkillById).toList();
        List<Candidate> result = candidates.stream().filter(candidate -> candidate.getSkills().containsAll(skills)).toList();
        Pageable pageable = PageRequest.of(Math.toIntExact(page), CandidateServiceImpl.CANDIDATES_PER_PAGE_RETURNED);
        long start = pageable.getOffset();
        long end = Math.min(start + pageable.getPageSize(), result.size());
        if(start >= end){
            return new PageImpl<>(List.of());
        }
        return new PageImpl<>(result.subList((int) start, (int) end), pageable, result.size());
    }

}
