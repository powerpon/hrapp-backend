package com.intens.hrapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.intens.hrapp.models.Skill;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class SkillControllerTest {

    private final MockMvc mvc;
    @MockBean
    private SkillRepository skillRepository;

    private final Skill testSkill = new Skill(
            1L,
            "java",
            new HashSet<>()
    );

    private final ObjectWriter objectWriter = new ObjectMapper().writer();

    @Autowired
    public SkillControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    void getSkill() throws Exception {
        Mockito.when(skillRepository.findById(1L)).thenReturn(Optional.of(testSkill));
        mvc.perform(
                MockMvcRequestBuilders.get("/skill/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllSkills() throws Exception  {
        Mockito.when(skillRepository.findAll(PageRequest.of(0, 5))).thenReturn(new PageImpl<>(new ArrayList<>()));
        mvc.perform(
                MockMvcRequestBuilders.get("/skill/all")
                        .param("page", "0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createSkill() throws Exception  {
        mvc.perform(
                MockMvcRequestBuilders.post("/skill/create")
                        .content(objectWriter.writeValueAsString(testSkill))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void updateSkill() throws Exception  {
        Mockito.when(skillRepository.findById(1L)).thenReturn(Optional.of(testSkill));
        mvc.perform(
                MockMvcRequestBuilders.put("/skill/1/edit")
                        .content(objectWriter.writeValueAsString(testSkill))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteSkill() throws Exception  {
        Mockito.when(skillRepository.findById(1L)).thenReturn(Optional.of(testSkill));
        mvc.perform(
                MockMvcRequestBuilders.delete("/skill/1/delete")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

}