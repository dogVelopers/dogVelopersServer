package com.dogvelopers.dogvelopers.repository;

import com.dogvelopers.dogvelopers.entity.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;

    @Test
    @DisplayName("project 목록 가장 최근 시작한 순서로 조회")
    void findAllOrderByStartDate() {
        // given
        Project p1 = generateProject(1);
        Project p2 = generateProject(2);
        Project p3 = generateProject(3);

        projectRepository.save(p1);
        projectRepository.save(p2);
        projectRepository.save(p3);

        // when
        PageRequest pageRequest = PageRequest.of(0, 3);
        List<Project> projects = projectRepository.findProjectsOrderByStartDate(pageRequest);

        // then
        assertEquals(projects.size(), 3);
    }

    Project generateProject(int index) {
        return Project.builder()
                .name("제목" + index)
                .description("이런 프로젝트")
                .startDate(LocalDate.of(2022, index, index))
                .endDate(LocalDate.of(2022, index + 2, 1))
                .build();
    }
}
