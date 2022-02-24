package com.dogvelopers.dogvelopers.dto.hof;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class HofAdminDto {
    Long id;
    Long memberId;
    String company;
    String introduction;

    @Builder
    public HofAdminDto(Long id , Long memberId , String company, String introduction){
        this.id = id;
        this.memberId = memberId;
        this.company = company;
        this.introduction = introduction;
    }
}
