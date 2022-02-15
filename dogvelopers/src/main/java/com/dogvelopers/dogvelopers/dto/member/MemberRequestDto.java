package com.dogvelopers.dogvelopers.dto;

import com.dogvelopers.dogvelopers.entity.Member;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberRequestDto {

    private String name;

    private String studentId;

    private String major;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime birthDay;

    @Builder
    public Member toEntity(){
        return Member.builder()
                .name(name)
                .studentId(studentId)
                .major(major)
                .birthDay(birthDay)
                .build();
    }
}
