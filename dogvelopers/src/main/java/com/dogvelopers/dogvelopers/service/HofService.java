package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.dto.hof.HofRequestDto;
import com.dogvelopers.dogvelopers.dto.hof.HofResponseDto;
import com.dogvelopers.dogvelopers.dto.hof.SortByDate;
import com.dogvelopers.dogvelopers.dto.member.MemberResponseDto;
import com.dogvelopers.dogvelopers.entity.Hof;
import com.dogvelopers.dogvelopers.entity.Member;
import com.dogvelopers.dogvelopers.handler.CustomException;
import com.dogvelopers.dogvelopers.repository.HofRepository;
import com.dogvelopers.dogvelopers.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
        List<Hof> hofs = hofRepository.findAll();
        Collections.sort(hofs , new SortByDate()); // 기수 순으로 정렬
        for(Hof hof : hofs){
            hofResponseDtos.add(new HofResponseDto(hof));
        }
        return hofResponseDtos;
    }

    @Transactional
    public List<HofResponseDto> findByJoinDate(Long year){ // 기수 별로 조회
        List<HofResponseDto> hofResponseDtos = new ArrayList<>();
        List<Hof> hofs = hofRepository.findAll();
        Collections.sort(hofs , new SortByDate());
        for(Hof hof : hofs){
            if(hof.getMember().getJoinDate().getYear() == year) hofResponseDtos.add(new HofResponseDto(hof));
        }
        return hofResponseDtos;
    }

    @Transactional(rollbackFor = Exception.class)
    public HofResponseDto save(HofRequestDto hofRequestDto){
        // introduction , company 가 비어있거나 , null 인 경우 예외 발생
        if(exceptionCheck(hofRequestDto)) {
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

    @Transactional(rollbackFor = Exception.class)
    public HofResponseDto update(Long id , HofRequestDto hofRequestDto){

        // introduction , company 가 비어있거나 , null 인 경우 예외 발생
        if(exceptionCheck(hofRequestDto)) throw new CustomException(BAD_REQUEST_INFO);

        // member로 등록되지 않은 사람을 등록하였을 때 예외 발생
        if(!memberRepository.existsById(hofRequestDto.getMemberId())){
            throw new CustomException(NOT_FOUND_INFO);
        }

        Hof hof = hofRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        hofRequestDto.setMember(memberRepository.findById(id).get());
        hof.updateHof(hofRequestDto.toEntity());

        return new HofResponseDto(hofRepository.save(hof));
    }

    @Transactional
    public void delete(Long id){
        // id를 이용해서 지우면 됨
        Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        memberRepository.delete(member);
    }

    // exception 체크
    public boolean exceptionCheck(HofRequestDto hofRequestDto){
        if(hofRequestDto.getIntroduction() == null || hofRequestDto.getIntroduction().isBlank()
                || hofRequestDto.getCompany() == null || hofRequestDto.getCompany().isBlank()) return true;
        return false;
    }

}
