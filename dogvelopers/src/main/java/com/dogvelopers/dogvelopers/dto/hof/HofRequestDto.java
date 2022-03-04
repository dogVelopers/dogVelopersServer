package com.dogvelopers.dogvelopers.dto.hof;

import com.dogvelopers.dogvelopers.entity.Hof;
import com.dogvelopers.dogvelopers.entity.Member;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class HofRequestDto {

    private Long memberId;
    private Member member;
    private String company;
    private String introduction;

    public Hof toEntity(){
        return Hof.builder()
                .member(member)
                .company(company)
                .introduction(introduction)
                .build();
    }

    @Builder
    public HofRequestDto(Long memberId , String company , String introduction){
        this.memberId = memberId;
        this.company = company;
        this.introduction = introduction;
    }
}
