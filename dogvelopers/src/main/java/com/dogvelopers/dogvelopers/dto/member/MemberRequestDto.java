package com.dogvelopers.dogvelopers.dto.member;

import com.dogvelopers.dogvelopers.entity.Member;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberRequestDto {

    private String name;

    private Long generation;

    private String studentId;

    private String major;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDay;

    private String imageUrl;

    @Builder
    public Member toEntity(){
        return Member.builder()
                .name(name)
                .studentId(studentId)
                .major(major)
                .birthDay(birthDay)
                .generation(generation)
                .imageUrl(imageUrl)
                .build();
    }
}
