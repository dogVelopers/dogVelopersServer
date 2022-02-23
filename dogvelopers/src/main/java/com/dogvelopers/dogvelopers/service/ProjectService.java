package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.dto.project.ProjectResponseDto;
import com.dogvelopers.dogvelopers.dto.project.ProjectSaveRequestDto;
import com.dogvelopers.dogvelopers.entity.Project;
import com.dogvelopers.dogvelopers.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<ProjectResponseDto> findAll() {
        List<ProjectResponseDto> dtoList = new ArrayList<>();
        List<Project> projectList = projectRepository.findAll();
        for (Project project : projectList) {
            dtoList.add(new ProjectResponseDto(project));
        }
        return dtoList;
    }

    public ProjectResponseDto findById(Long id) {
        var project = projectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new ProjectResponseDto(project);
    }

    @Transactional
    public Long save(ProjectSaveRequestDto requestDto) {
        return projectRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, ProjectSaveRequestDto requestDto) {
        Project project = projectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        project.updateProject(requestDto.toEntity());
        return project.getId();
    }

    @Transactional
    public void delete(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        projectRepository.delete(project);
    }

}
