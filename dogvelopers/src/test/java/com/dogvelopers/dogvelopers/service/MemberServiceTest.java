package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.dto.project.ProjectResponseDto;
import com.dogvelopers.dogvelopers.dto.project.ProjectSaveRequestDto;
import com.dogvelopers.dogvelopers.repository.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectService projectService;

    @Test
    @DisplayName("dto를 받아 저장하고 Project를 조회")
    void save() {
        // given
        ProjectSaveRequestDto dto = generateDto();

        // when
        Long saveId = projectService.save(dto);
        ProjectResponseDto responseDto = projectService.findById(saveId);

        // then
        assertAll(
                () -> assertEquals(responseDto.getId(), saveId),
                () -> assertEquals(responseDto.getName(), dto.getName()),
                () -> assertEquals(responseDto.getDescription(), dto.getDescription()),
                () -> assertEquals(responseDto.getStartDate(), dto.getStartDate()),
                () -> assertEquals(responseDto.getEndDate(), dto.getEndDate())
        );
    }

    @Test
    @DisplayName("Project 갱신하고 조회")
    void update() {
        ProjectSaveRequestDto projectSaveRequestDto = generateDto();

        ProjectSaveRequestDto updateDto = ProjectSaveRequestDto.builder()
                .name("수정")
                .description("수정")
                .startDate(LocalDate.of(2022, 01, 02))
                .endDate(LocalDate.of(2022, 02, 02))
                .build();

        Long saveId = projectService.save(projectSaveRequestDto);
        Long updateId = projectService.update(saveId, updateDto);

        ProjectResponseDto findUpdateDto = projectService.findById(updateId);

        assertAll(
                () -> assertEquals(findUpdateDto.getId(), saveId),
                () -> assertEquals(findUpdateDto.getName(), updateDto.getName()),
                () -> assertEquals(findUpdateDto.getDescription(), updateDto.getDescription()),
                () -> assertEquals(findUpdateDto.getStartDate(), updateDto.getStartDate()),
                () -> assertEquals(findUpdateDto.getEndDate(), updateDto.getEndDate())
        );
    }

    @Test
    @DisplayName("존재하지 않는 게시물을 수정하여 에러를 던짐")
    void update_error() {
        // given
        ProjectSaveRequestDto updateDto = generateDto();

        // when, then
        assertThrows(EntityNotFoundException.class, () ->
                projectService.update(1L, updateDto));
    }

    @Test
    @DisplayName("Project 삭제")
    void delete() {
        // given
        ProjectSaveRequestDto projectSaveRequestDto = generateDto();
        Long saveId = projectService.save(projectSaveRequestDto);

        // when
        projectService.delete(saveId);

        // then
        assertEquals(projectRepository.findAll().size(), 0);
    }

    @Test
    @DisplayName("없는 Project 삭제")
    void delete_error() {
        // given
        ProjectSaveRequestDto deleteDto = generateDto();
        Long saveId = projectService.save(deleteDto);

        // when
        projectService.delete(saveId);

        // then
        assertThrows(EntityNotFoundException.class, () ->
                projectService.delete(saveId));
    }

    private ProjectSaveRequestDto generateDto() {
        return ProjectSaveRequestDto.builder()
                .name("Project Name")
                .description("Project Description")
                .startDate(LocalDate.of(2022, 01, 01))
                .endDate(LocalDate.of(2022, 02, 01))
                .build();
    }
}
