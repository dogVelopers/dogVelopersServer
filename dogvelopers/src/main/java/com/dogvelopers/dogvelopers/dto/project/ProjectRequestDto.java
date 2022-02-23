package com.dogvelopers.dogvelopers.dto.project;

import com.dogvelopers.dogvelopers.entity.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ProjectRequestDto {

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public Project toEntity(){
        return Project.builder()
                .name(name)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
