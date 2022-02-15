package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.dto.HofRequestDto;
import com.dogvelopers.dogvelopers.dto.HofResponseDto;
import com.dogvelopers.dogvelopers.entity.Hof;
import com.dogvelopers.dogvelopers.entity.Member;
import com.dogvelopers.dogvelopers.handler.CustomException;
import com.dogvelopers.dogvelopers.repository.HofRepository;
import com.dogvelopers.dogvelopers.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.dogvelopers.dogvelopers.enumType.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class HofService {

    private final HofRepository hofRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public List<HofResponseDto> findAll(){
        List<HofResponseDto> hofResponseDtos = new ArrayList<>();
        for(Hof hof : hofRepository.findAll()){
            hofResponseDtos.add(new HofResponseDto(hof));
        }
        return hofResponseDtos;
    }

    @Transactional(rollbackFor = Exception.class)
    public HofResponseDto save(HofRequestDto hofRequestDto){
        // introduction , company 가 비어있거나 , null 인 경우 예외 발생
        if(hofRequestDto.getIntroduction() == null || hofRequestDto.getIntroduction().isBlank() || hofRequestDto.getCompany() == null || hofRequestDto.getCompany().isBlank()) {
            throw new CustomException(BAD_REQUEST_INFO);
        }
        // member로 등록되지 않은 사람을 등록하였을 때 예외 발생
        if(!memberRepository.existsById(hofRequestDto.getMemberId())){
            throw new CustomException(NOT_FOUND_INFO);
        }

        // 이미 명예의 전당에 등록되어 있는 사람이면 예외 발생
        if(hofRepository.existsByMemberId(hofRequestDto.getMemberId())){
            throw new CustomException(DUPLICATE_INFO);
        }

        hofRequestDto.setMember(memberRepository.findById(hofRequestDto.getMemberId()).get());
        return new HofResponseDto(hofRepository.save(hofRequestDto.toEntity()));
    }
}
