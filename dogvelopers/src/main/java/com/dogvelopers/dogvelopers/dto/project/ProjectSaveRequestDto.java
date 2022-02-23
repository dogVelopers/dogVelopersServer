package com.dogvelopers.dogvelopers.dto.project;

import com.dogvelopers.dogvelopers.entity.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ProjectSaveRequestDto {

    private Long postId;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public ProjectSaveRequestDto(Long postId, String name, String description, LocalDate startDate, LocalDate endDate) {
        this.postId = postId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Project toEntity() {
        return Project.builder()
                .name(this.name)
                .description(this.description)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
