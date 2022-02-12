package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.dto.MemberRequestDto;
import com.dogvelopers.dogvelopers.dto.MemberResponseDto;
import com.dogvelopers.dogvelopers.entity.Member;
import com.dogvelopers.dogvelopers.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto save(MemberRequestDto memberRequestDto){
        return new MemberResponseDto(memberRepository.save(memberRequestDto.toEntity()));
    }

    public List<MemberResponseDto> findAll(){
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();
        for(Member member : memberRepository.findAll()){
            memberResponseDtos.add(new MemberResponseDto(member));
        }
        return memberResponseDtos;
    }
}
