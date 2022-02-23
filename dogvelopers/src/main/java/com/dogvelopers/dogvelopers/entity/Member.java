package com.dogvelopers.dogvelopers.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STUDENT_ID")
    private String studentId;

    @Column(name = "MAJOR")
    private String major;

    @Column(name = "GENERATION")
    private Long generation;

    @Column(name = "BIRTH_DAY")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // 한번 이렇게 해보자.
    private LocalDate birthDay;


    @Builder
    public Member(String name , String studentId , String major , LocalDate birthDay , Long generation){
        this.name = name;
        this.studentId = studentId;
        this.major = major;
        this.birthDay = birthDay;
        this.generation = generation;
    }

    public void updateMember(Member member){
        this.name = member.getName();
        this.studentId = member.getStudentId();
        this.major = member.getMajor();
        this.birthDay = member.getBirthDay();
        this.generation = member.getGeneration();
    }
}
