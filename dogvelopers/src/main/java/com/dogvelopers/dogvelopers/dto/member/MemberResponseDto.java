package com.dogvelopers.dogvelopers.dto;

import com.dogvelopers.dogvelopers.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResponseDto {
    private Long id;
    private String name;
    private String studentId;
    private String major;
    private LocalDateTime birthDay;

    public MemberResponseDto(Member member){
        this.id = member.getId();
        this.name = member.getName();
        this.studentId = member.getStudentId();
        this.major = member.getMajor();
        this.birthDay = member.getBirthDay();
    }
}