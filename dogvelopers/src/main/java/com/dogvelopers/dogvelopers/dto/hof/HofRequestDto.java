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

    @Builder
    public Hof toEntity(){
        return Hof.builder()
                .member(member)
                .company(company)
                .introduction(introduction)
                .build();
    }
}
