package com.dogvelopers.dogvelopers.dto.project;

import com.dogvelopers.dogvelopers.entity.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ProjectSaveRequestDto {

    private Long postId;

    @NotEmpty(message = "프로젝트 제목이 비어있습니다.")
    @Size(max = 30, message="TITLE의 길이는 30을 넘을 수 없습니다.")
    private String name;

    @NotEmpty(message = "프로젝트 설명이 비어있습니다.")
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
