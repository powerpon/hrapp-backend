package com.intens.hrapp.services.interfaces;

import com.intens.hrapp.dto.CandidateDTO;
import com.intens.hrapp.models.Candidate;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CandidateService {

    Candidate createCandidate(Candidate candidate);
    Candidate updateCandidateDetails(String id, CandidateDTO candidateDTO);
    Candidate getCandidateById(String id);
    Page<Candidate> getAllCandidates(Long page);
    void deleteCandidateById(String id);
    Candidate addSkill(String candidateId, Long skillId);
    Candidate removeSkill(String candidateId, Long skillId);
    Page<Candidate> getCandidatesByName(String name, Long page);
    Page<Candidate> getCandidatesBySkills(List<Long> skillIds, Long page);

}
