package com.intens.hrapp.controllers;

import com.intens.hrapp.dto.SkillDTO;
import com.intens.hrapp.models.Skill;
import com.intens.hrapp.services.interfaces.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/skill")
@Tag(name = "skills", description = "Dealing with skills")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.POST})
public class SkillController {

    private final SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get skill by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skill found"),
            @ApiResponse(responseCode = "404", description = "Skill not found")
    })
    public ResponseEntity<Skill> getSkill(@PathVariable Long id){
        return ResponseEntity.ok(skillService.getSkillById(id));
    }

    @GetMapping(value = "/all", params = "page")
    @Operation(summary = "Get all skills by page")
    public ResponseEntity<Page<Skill>> getAllSkills(@RequestParam(defaultValue = "0") Long page){
        return ResponseEntity.ok(skillService.getAllSkills(page));
    }

    @PostMapping("/create")
    @Operation(summary = "Create new skill")
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill){
        return ResponseEntity.status(HttpStatus.CREATED).body(skillService.createSkill(skill));
    }

    @PutMapping("/{id}/edit")
    @Operation(summary = "Update skill's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skill found"),
            @ApiResponse(responseCode = "404", description = "Skill not found")
    })
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @RequestBody SkillDTO skillDTO){
        return ResponseEntity.ok(skillService.updateSkill(id, skillDTO));
    }

    @DeleteMapping("/{id}/delete")
    @Operation(summary = "Delete skill by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skill found"),
            @ApiResponse(responseCode = "404", description = "Skill not found")
    })
    public ResponseEntity<?> deleteSkill(@PathVariable Long id){
        skillService.deleteSkillById(id);
        return ResponseEntity.ok().build();
    }

}
