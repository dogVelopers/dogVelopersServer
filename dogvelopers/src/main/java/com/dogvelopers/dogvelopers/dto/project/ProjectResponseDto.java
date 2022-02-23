package com.dogvelopers.dogvelopers.dto.project;

import com.dogvelopers.dogvelopers.entity.Project;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ProjectResponseDto implements Comparable<ProjectResponseDto> {

    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public ProjectResponseDto(Project project){
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
    }

    @Override
    public int compareTo(ProjectResponseDto dto) {
        return this.startDate.compareTo(dto.startDate);
    }
}
