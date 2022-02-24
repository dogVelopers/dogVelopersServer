package com.dogvelopers.dogvelopers.dto.project;

import com.dogvelopers.dogvelopers.entity.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ProjectRequestDto {

    private String name;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
