package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.dto.ProjectResponseDto;
import com.dogvelopers.dogvelopers.entity.Project;
import com.dogvelopers.dogvelopers.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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

}
