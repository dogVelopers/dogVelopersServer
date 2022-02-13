package com.dogvelopers.dogvelopers.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "HOF")
public class Hof {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ID")
    private Member member;

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "INTRODUCTION")
    private String introduction;

    @Builder
    public Hof(Member member , String company,  String introduction){
        this.member = member;
        this.company = company;
        this.introduction =introduction;
    }
}
