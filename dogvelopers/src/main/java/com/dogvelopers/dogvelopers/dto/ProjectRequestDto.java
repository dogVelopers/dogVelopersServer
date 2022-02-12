package com.dogvelopers.dogvelopers.dto;

import com.dogvelopers.dogvelopers.entity.Project;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProjectRequestDto {

    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder
    public Project toEntity(Project project){
        return Project.builder()
                .name(name)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
