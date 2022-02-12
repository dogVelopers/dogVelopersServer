package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.dto.MemberRequestDto;
import com.dogvelopers.dogvelopers.dto.MemberResponseDto;
import com.dogvelopers.dogvelopers.entity.Member;
import com.dogvelopers.dogvelopers.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto save(MemberRequestDto memberRequestDto){

        // 생일이 잘못 되어 있으면
        if(memberRequestDto.getBirthDay() == null || memberRequestDto.getBirthDay().getYear() > LocalDateTime.now().getYear()){
            return null; // exception 처리
        }

        // 이름 , 학번 , 전공 입력이 안되어있으면
        if(memberRequestDto.getStudentId() == null || memberRequestDto.getStudentId().isBlank() || memberRequestDto.getName() == null || memberRequestDto.getName().isBlank() || memberRequestDto.getMajor() == null || memberRequestDto.getMajor().isBlank()){
            return null; // exception 처리
        }

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
