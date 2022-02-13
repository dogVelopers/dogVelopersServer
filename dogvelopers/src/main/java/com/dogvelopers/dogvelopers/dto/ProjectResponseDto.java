package com.dogvelopers.dogvelopers.dto;

import com.dogvelopers.dogvelopers.entity.Project;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ProjectResponseDto {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public ProjectResponseDto(Project project){
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
    }
}
