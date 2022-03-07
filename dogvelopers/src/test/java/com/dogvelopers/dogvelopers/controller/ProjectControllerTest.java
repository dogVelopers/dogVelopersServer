package com.dogvelopers.dogvelopers.controller;

import com.dogvelopers.dogvelopers.dto.project.ProjectSaveRequestDto;
import com.dogvelopers.dogvelopers.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;

@SpringBootTest
@Transactional
public class ProjectControllerTest {

    MockMvc mockMvc;

    @Autowired
    ProjectService projectService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("POST /projects")
    void save() throws Exception {
        // given
        ProjectSaveRequestDto a = generateDto("A");

        // when
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(a));

        // then
        mockMvc.perform(content)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("PUT /projects/{projectId}")
    void update() throws Exception {
        // given
        ProjectSaveRequestDto saveDto = generateDto("CREATE");

        // when
        Long saveId = projectService.save(saveDto);
        ProjectSaveRequestDto updateDto = generateDto("UPDATE");

        // then
        MockHttpServletRequestBuilder updateContent = MockMvcRequestBuilders.put("/projects/{saveId}", saveId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto));

        mockMvc.perform(updateContent)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("DELETE /projects/{projectId}")
    void delete() throws Exception {
        // given
        ProjectSaveRequestDto deleteDto = generateDto("DELETE");

        // when
        Long saveId = projectService.save(deleteDto);

        // then
        MockHttpServletRequestBuilder deleteContent = MockMvcRequestBuilders.delete("/projects/{saveId}", saveId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteDto));

        mockMvc.perform(deleteContent)
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

    }

    static private ProjectSaveRequestDto generateDto(String s) {
        return ProjectSaveRequestDto.builder()
                .name("제목" + s)
                .description("설명" + s)
                .startDate(LocalDate.of(2022, 01, 01))
                .endDate(LocalDate.of(2022, 02, 01))
                .build();
    }

}