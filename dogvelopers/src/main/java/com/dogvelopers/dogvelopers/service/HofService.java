package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.dto.HofRequestDto;
import com.dogvelopers.dogvelopers.dto.HofResponseDto;
import com.dogvelopers.dogvelopers.entity.Hof;
import com.dogvelopers.dogvelopers.repository.HofRepository;
import com.dogvelopers.dogvelopers.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HofService {

    private final HofRepository hofRepository;
    private final MemberRepository memberRepository;

    public List<HofResponseDto> findAll(){
        List<HofResponseDto> hofResponseDtos = new ArrayList<>();
        for(Hof hof : hofRepository.findAll()){
            hofResponseDtos.add(new HofResponseDto(hof));
        }
        return hofResponseDtos;
    }

    public HofResponseDto save(HofRequestDto hofRequestDto){
        // introduction , company 가 비어있거나 , null 인 경우 예외 발생
        if(hofRequestDto.getIntroduction() == null || hofRequestDto.getIntroduction().isBlank() || hofRequestDto.getCompany() == null || hofRequestDto.getCompany().isBlank()) {
            return null; // exception 정의해야 할 것 같아요
        }
        // member로 등록되지 않은 사람을 등록하였을 때 예외 발생
        if(!memberRepository.existsById(hofRequestDto.getMemberId())){
            return null; // exception 정의해야 할 것 같아요
        }

        hofRequestDto.setMember(memberRepository.findById(hofRequestDto.getMemberId()).get());
        return new HofResponseDto(hofRepository.save(hofRequestDto.toEntity()));
    }
}
