package com.intens.hrapp.controllers;

import com.intens.hrapp.dto.CandidateDTO;
import com.intens.hrapp.models.Candidate;
import com.intens.hrapp.services.interfaces.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidate")
@Tag(name = "candidates", description = "Dealing with candidates")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.POST})
public class CandidateController {

    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get candidate by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidate found"),
            @ApiResponse(responseCode = "404", description = "Candidate not found")
    })
    public ResponseEntity<Candidate> getCandidate(@PathVariable String id){
        return ResponseEntity.ok(candidateService.getCandidateById(id));
    }

    @GetMapping(value = "/all", params = "page")
    @Operation(summary = "Get all users by page")
    public ResponseEntity<Page<Candidate>> getAllCandidates(@RequestParam(defaultValue = "0") Long page){
        return ResponseEntity.ok(candidateService.getAllCandidates(page));
    }

    @PostMapping("/create")
    @Operation(summary = "Create new candidate")
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate){
        return ResponseEntity.status(HttpStatus.CREATED).body(candidateService.createCandidate(candidate));
    }

    @PutMapping("/{id}/edit")
    @Operation(summary = "Update candidate's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidate found"),
            @ApiResponse(responseCode = "404", description = "Candidate not found")
    })
    public ResponseEntity<Candidate> updateCandidateDetails(@PathVariable String id, @RequestBody CandidateDTO candidateDTO){
        return ResponseEntity.ok(candidateService.updateCandidateDetails(id, candidateDTO));
    }

    @DeleteMapping("/{id}/delete")
    @Operation(summary = "Delete candidate by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidate found"),
            @ApiResponse(responseCode = "404", description = "Candidate not found")
    })
    public ResponseEntity<?> deleteCandidate(@PathVariable String id){
        candidateService.deleteCandidateById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{candidateId}/add/skill/{skillId}")
    @Operation(summary = "Add skill to candidate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidate and skill found"),
            @ApiResponse(responseCode = "404", description = "Candidate or skill not found")
    })
    public ResponseEntity<Candidate> addSkillToCandidate(@PathVariable String candidateId, @PathVariable Long skillId){
        return ResponseEntity.ok(candidateService.addSkill(candidateId, skillId));
    }

    @PatchMapping("/{candidateId}/remove/skill/{skillId}")
    @Operation(summary = "Remove skill from candidate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidate and skill found"),
            @ApiResponse(responseCode = "404", description = "Candidate or skill not found")
    })
    public ResponseEntity<Candidate> removeSkillFromCandidate(@PathVariable String candidateId, @PathVariable Long skillId){
        return ResponseEntity.ok(candidateService.removeSkill(candidateId, skillId));
    }

    @GetMapping(value = "/search/name", params = "page")
    @Operation(summary = "Get candidates by name")
    public ResponseEntity<Page<Candidate>> getCandidatesByName(@RequestParam String name, @RequestParam(defaultValue = "0") Long page){
        return ResponseEntity.ok(candidateService.getCandidatesByName(name, page));
    }

    @GetMapping(value = "/skills", params = {"skillIds", "page"})
    @Operation(summary = "Get candidates by skill IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skill/s found"),
            @ApiResponse(responseCode = "404", description = "Skill/s not found")
    })
    public ResponseEntity<Page<Candidate>> getCandidatesBySkills(@RequestParam List<Long> skillIds, @RequestParam(defaultValue = "0") Long page){
        return ResponseEntity.ok(candidateService.getCandidatesBySkills(skillIds, page));
    }

}
