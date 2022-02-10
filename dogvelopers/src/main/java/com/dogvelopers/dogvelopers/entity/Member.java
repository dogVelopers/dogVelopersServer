package com.dogvelopers.dogvelopers.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STUDENT_ID")
    private Long studentId;

    @Column(name = "MAJOR")
    private String major;

    @Column(name = "BIRTH_DAY")
    private LocalDateTime birthDay;
}
