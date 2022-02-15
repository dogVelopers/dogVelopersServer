package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.dto.MemberRequestDto;
import com.dogvelopers.dogvelopers.dto.MemberResponseDto;
import com.dogvelopers.dogvelopers.entity.Member;
import com.dogvelopers.dogvelopers.enumType.ErrorCode;
import com.dogvelopers.dogvelopers.handler.CustomException;
import com.dogvelopers.dogvelopers.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dogvelopers.dogvelopers.enumType.ErrorCode.BAD_REQUEST_INFO;
import static com.dogvelopers.dogvelopers.enumType.ErrorCode.DUPLICATE_INFO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(rollbackFor = Exception.class)
    public MemberResponseDto save(MemberRequestDto memberRequestDto){

        // 생일이 잘못 되어 있고 , 이름 , 학번 , 전공 입력이 안되어있으면
        if(memberRequestDto.getBirthDay() == null || memberRequestDto.getBirthDay().getYear() > LocalDateTime.now().getYear() || memberRequestDto.getStudentId() == null || memberRequestDto.getStudentId().isBlank() || memberRequestDto.getName() == null || memberRequestDto.getName().isBlank() || memberRequestDto.getMajor() == null || memberRequestDto.getMajor().isBlank()){
            throw new CustomException(BAD_REQUEST_INFO); // exception 처리
        }

        return new MemberResponseDto(memberRepository.save(memberRequestDto.toEntity()));
    }

    @Transactional
    public List<MemberResponseDto> findAll(){
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();
        for(Member member : memberRepository.findAll()){
            memberResponseDtos.add(new MemberResponseDto(member));
        }
        return memberResponseDtos;
    }
}
