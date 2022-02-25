package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.dto.member.MemberRequestDto;
import com.dogvelopers.dogvelopers.dto.member.MemberResponseDto;
import com.dogvelopers.dogvelopers.entity.Member;
import com.dogvelopers.dogvelopers.handler.CustomException;
import com.dogvelopers.dogvelopers.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static com.dogvelopers.dogvelopers.enumType.ErrorCode.BAD_REQUEST_INFO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(rollbackFor = Exception.class)
    public MemberResponseDto save(MemberRequestDto memberRequestDto) {

        // 생일이 잘못 되어 있고 , 이름 , 학번 , 전공 입력이 안되어있으면
        if (exceptionCheck(memberRequestDto)) {
            throw new CustomException(BAD_REQUEST_INFO); // exception 처리
        }

        return new MemberResponseDto(memberRepository.save(memberRequestDto.toEntity()));
    }

    @Transactional
    public List<MemberResponseDto> findAll() { // 기수의 역순으로 반환되게 끔 설정
//        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();
//
//        for(Member member : memberRepository.findAllByOrderByGenerationDesc()){
//            memberResponseDtos.add(new MemberResponseDto(member));
//        }

//        return memberResponseDtos;
        return memberRepository.findAllByOrderByGenerationDesc().stream()
                .map(member -> new MemberResponseDto(member))
                .collect(Collectors.toList());
    }

    @Transactional
    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new MemberResponseDto(member);
    }

    @Transactional
    public List<MemberResponseDto> findByGeneration(Long generation) {
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();

        // 해당 기수만 조회
        for (Member member : memberRepository.findByGenerationOrderByGenerationDesc(generation)) {
            memberResponseDtos.add(new MemberResponseDto(member));
        }

        return memberResponseDtos;
    }

    @Transactional(rollbackFor = Exception.class)
    public MemberResponseDto update(Long id, MemberRequestDto memberRequestDto) {
        // id 를 받아서 , 해당 객체를 뽑아서 , 해당 id 로 레코드의 내용을 바꾼다.
        if (exceptionCheck(memberRequestDto)) throw new CustomException(BAD_REQUEST_INFO);
        Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        member.updateMember(memberRequestDto.toEntity());
        return new MemberResponseDto(memberRepository.save(member));
    }

    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        memberRepository.delete(member);
    }

    public boolean existsById(Long id) {
        return memberRepository.existsById(id);
    }

    // 입력에 이상이 있나 체크
    public boolean exceptionCheck(MemberRequestDto memberRequestDto) {
        if (memberRequestDto.getBirthDay() == null || memberRequestDto.getBirthDay().getYear() > LocalDateTime.now().getYear()
                || checkNullBlank(memberRequestDto.getStudentId())
                || checkNullBlank(memberRequestDto.getName())
                || checkNullBlank(memberRequestDto.getMajor()))
            return true; // exception 처리
        return false; // 예외 안 걸림
    }

    public boolean checkNullBlank(String string) {
        if (string == null || string.isBlank()) return true;
        return false;
    }
}
