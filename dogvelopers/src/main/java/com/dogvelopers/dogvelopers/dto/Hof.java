package com.dogvelopers.dogvelopers.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "HOF")
public class Hof {
    @Column(name = "ID")
    private Long id;

    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "INTRODUCTION")
    private String introduction;
}
