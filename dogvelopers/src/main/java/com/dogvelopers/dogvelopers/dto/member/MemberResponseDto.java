package com.dogvelopers.dogvelopers.dto.member;

import com.dogvelopers.dogvelopers.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberResponseDto {
    private Long id;
    private String name;
    private String studentId;
    private String major;
    private LocalDate birthDay;
    private Long generation;
    private String imageUrl;

    public MemberResponseDto(Member member){
        this.id = member.getId();
        this.name = member.getName();
        this.studentId = member.getStudentId();
        this.major = member.getMajor();
        this.birthDay = member.getBirthDay();
        this.generation = member.getGeneration();
        this.imageUrl = member.getImageUrl();
    }
}
