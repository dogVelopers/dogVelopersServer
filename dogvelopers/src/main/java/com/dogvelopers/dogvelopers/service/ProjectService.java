package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.dto.project.ProjectResponseDto;
import com.dogvelopers.dogvelopers.dto.project.ProjectSaveRequestDto;
import com.dogvelopers.dogvelopers.entity.Project;
import com.dogvelopers.dogvelopers.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<ProjectResponseDto> findAll(Pageable pageable) {
        List<ProjectResponseDto> dtoList = projectRepository.findProjectsOrderByStartDate(pageable).stream()
                .map(model -> new ProjectResponseDto(model))
                .collect(Collectors.toList());
        return dtoList;
    }

    public ProjectResponseDto findById(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
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
