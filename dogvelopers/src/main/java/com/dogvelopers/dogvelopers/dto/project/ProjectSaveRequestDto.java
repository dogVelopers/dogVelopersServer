package com.dogvelopers.dogvelopers.dto.project;

import com.dogvelopers.dogvelopers.entity.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProjectSaveRequestDto {

    private Long postId;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder
    public ProjectSaveRequestDto(Long postId, String name, String description, LocalDateTime startDate, LocalDateTime endDate) {
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
