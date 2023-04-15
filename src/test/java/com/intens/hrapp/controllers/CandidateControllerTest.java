package com.intens.hrapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.intens.hrapp.models.Candidate;
import com.intens.hrapp.models.Skill;
import com.intens.hrapp.repositories.CandidateRepository;
import com.intens.hrapp.repositories.SkillRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class CandidateControllerTest {

    private final MockMvc mvc;
    @MockBean
    private CandidateRepository candidateRepository;
    @MockBean
    private SkillRepository skillRepository;

    private final Candidate testCandidate = new Candidate(
            "123",
            "some name",
            Date.valueOf("1999-10-10"),
            "123-123-123",
            "random@gmail.com",
            new HashSet<>()
    );

    private final Skill testSkill = new Skill(
        1L,
        "java",
        new HashSet<>()
    );

    private final ObjectWriter objectWriter = new ObjectMapper().writer();

    @Autowired
    public CandidateControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    void getCandidate() throws Exception {
        Mockito.when(candidateRepository.findById("123")).thenReturn(Optional.of(testCandidate));
        mvc.perform(
                MockMvcRequestBuilders.get("/candidate/123")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllCandidates() throws Exception {
        Mockito.when(candidateRepository.findAll(PageRequest.of(0, 5))).thenReturn(new PageImpl<>(new ArrayList<>()));
        mvc.perform(
          MockMvcRequestBuilders.get("/candidate/all")
                  .param("page", "0")
                  .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createCandidate() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/candidate/create")
                        .content(objectWriter.writeValueAsString(testCandidate))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void updateCandidateDetails() throws Exception {
        Mockito.when(candidateRepository.findById("123")).thenReturn(Optional.of(testCandidate));
        mvc.perform(
          MockMvcRequestBuilders.put("/candidate/123/edit")
                  .content(objectWriter.writeValueAsString(testCandidate))
                  .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteCandidate() throws Exception {
        Mockito.when(candidateRepository.findById("123")).thenReturn(Optional.of(testCandidate));
        mvc.perform(
                MockMvcRequestBuilders.delete("/candidate/123/delete")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void addSkillToCandidate() throws Exception {
        Mockito.when(candidateRepository.findById("123")).thenReturn(Optional.of(testCandidate));
        Mockito.when(skillRepository.findById(1L)).thenReturn(Optional.of(testSkill));
        mvc.perform(
                MockMvcRequestBuilders.patch("/candidate/123/add/skill/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void removeSkillFromCandidate() throws Exception {
        Mockito.when(candidateRepository.findById("123")).thenReturn(Optional.of(testCandidate));
        Mockito.when(skillRepository.findById(1L)).thenReturn(Optional.of(testSkill));
        mvc.perform(
                MockMvcRequestBuilders.patch("/candidate/123/remove/skill/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getCandidatesByName() throws Exception {
        Mockito.when(candidateRepository.findAllByNameIgnoreCase(PageRequest.of(0, 5), "some name")).thenReturn(new PageImpl<>(new ArrayList<>()));
        mvc.perform(
                MockMvcRequestBuilders.get("/candidate/search/name")
                        .param("name", "some name")
                        .param("page", "0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getCandidatesBySkills() throws Exception {
        Mockito.when(skillRepository.findById(1L)).thenReturn(Optional.of(testSkill));
        Mockito.when(skillRepository.findById(2L)).thenReturn(Optional.of(testSkill));
        mvc.perform(
                MockMvcRequestBuilders.get("/candidate/skills")
                        .param("skillIds", "1,2")
                        .param("page", "0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}