package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.dto.member.MemberRequestDto;
import com.dogvelopers.dogvelopers.dto.member.MemberResponseDto;
import com.dogvelopers.dogvelopers.entity.Member;
import com.dogvelopers.dogvelopers.handler.CustomException;
import com.dogvelopers.dogvelopers.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dogvelopers.dogvelopers.enumType.ErrorCode.BAD_REQUEST_INFO;

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
    public List<MemberResponseDto> findAll(){ // 기수의 역순으로 반환되게 끔 설정
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();

        for(Member member : memberRepository.findAllByOrderByJoinDateDesc()){
            memberResponseDtos.add(new MemberResponseDto(member));
        }

        return memberResponseDtos;
    }

    @Transactional
    public List<MemberResponseDto> findByJoinDate(Long year){
        // year 을 받아서 , 해당 연도를 검색
        LocalDateTime startDate = LocalDateTime.of(year.intValue() , 1 , 1 , 0 , 0 , 0);
        LocalDateTime endDate = LocalDateTime.of(year.intValue() , 12 , 31 , 23 , 59 , 59);

        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();

        for(Member member : memberRepository.findByJoinDateBetweenOrderByJoinDateDesc(startDate , endDate)){
            memberResponseDtos.add(new MemberResponseDto(member));
        }

        return memberResponseDtos;
    }
}
