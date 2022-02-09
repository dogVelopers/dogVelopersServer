package com.dogvelopers.dogvelopers.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "MEMBER")
public class Member {

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
