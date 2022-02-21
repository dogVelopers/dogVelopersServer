package com.dogvelopers.dogvelopers.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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

    @Column(name = "BIRTH_DAY")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthDay;

    @Column(name = "JOIN_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinDate;

    @Builder
    public Member(String name , String studentId , String major , LocalDateTime birthDay , LocalDateTime joinDate){
        this.name = name;
        this.studentId = studentId;
        this.major = major;
        this.birthDay = birthDay;
        this.joinDate = joinDate;
    }
}
