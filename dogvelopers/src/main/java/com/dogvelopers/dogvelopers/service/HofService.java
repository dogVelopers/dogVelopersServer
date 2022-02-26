package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.dto.hof.HofRequestDto;
import com.dogvelopers.dogvelopers.dto.hof.HofResponseDto;
import com.dogvelopers.dogvelopers.dto.hof.SortByGeneration;
import com.dogvelopers.dogvelopers.entity.Hof;
import com.dogvelopers.dogvelopers.entity.Member;
import com.dogvelopers.dogvelopers.handler.CustomException;
import com.dogvelopers.dogvelopers.repository.HofRepository;
import com.dogvelopers.dogvelopers.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.dogvelopers.dogvelopers.enumType.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class HofService {

    private final HofRepository hofRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public HofResponseDto findById(Long id) {
        Hof hof = hofRepository.findById(id).orElseThrow(
                () -> {throw new CustomException(NOT_FOUND_INFO);}
        );
        return new HofResponseDto(hof);
    }

    @Transactional
    public List<HofResponseDto> findAll() {

        return hofRepository.findAll().stream()
                .sorted(new SortByGeneration())
                .map(hof -> new HofResponseDto(hof))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<HofResponseDto> findByGeneration(Long generation) { // 기수 별로 조회
        List<HofResponseDto> hofResponseDtos = new ArrayList<>();
        List<Hof> hofs = hofRepository.findAll();
        Collections.sort(hofs, new SortByGeneration());
        for (Hof hof : hofs) {
            if (hof.getMember().getGeneration() == generation) hofResponseDtos.add(new HofResponseDto(hof));
        }
        return hofResponseDtos;
    }

    @Transactional(rollbackFor = Exception.class)
    public HofResponseDto save(HofRequestDto hofRequestDto) {

        // introduction , company 가 비어있거나 , null 인 경우 예외 발생
        if (exceptionCheck(hofRequestDto)) {
            throw new CustomException(BAD_REQUEST_INFO);
        }
        // member로 등록되지 않은 사람을 등록하였을 때 예외 발생
        if (!memberRepository.existsById(hofRequestDto.getMemberId())) {
            throw new CustomException(NOT_FOUND_INFO);
        }

        // 이미 명예의 전당에 등록되어 있는 사람이면 예외 발생
        if (hofRepository.existsByMemberId(hofRequestDto.getMemberId())) {
            throw new CustomException(DUPLICATE_INFO);
        }

        hofRequestDto.setMember(memberRepository.findById(hofRequestDto.getMemberId()).get());

        return new HofResponseDto(hofRepository.save(hofRequestDto.toEntity()));
    }

    @Transactional(rollbackFor = Exception.class)
    public HofResponseDto update(Long id, HofRequestDto hofRequestDto) {

        // introduction , company 가 비어있거나 , null 인 경우 예외 발생
        if (exceptionCheck(hofRequestDto)) throw new CustomException(BAD_REQUEST_INFO);

        // member로 등록되지 않은 사람을 등록하였을 때 예외 발생
        Member member = memberRepository.findById(hofRequestDto.getMemberId())
                .orElseThrow(() -> {
                    throw new CustomException(NOT_FOUND_INFO);
                });

        Hof hof = hofRepository.findById(id).orElseThrow(
                () -> {throw new CustomException(NOT_FOUND_INFO);}
        );

        hofRequestDto.setMember(member); // member등록
        hof.updateHof(hofRequestDto.toEntity());

        return new HofResponseDto(hofRepository.save(hof));
    }

    @Transactional
    public void delete(Long id) {
        // id를 이용해서 지우면 됨
        Hof hof = hofRepository.findById(id).orElseThrow(
                () -> {throw new CustomException(NOT_FOUND_INFO);}
        );
        hofRepository.delete(hof);
    }

    @Transactional
    public boolean existsById(Long id) {
        return hofRepository.existsById(id);
    }

    // exception 체크
    public boolean exceptionCheck(HofRequestDto hofRequestDto) {
        if (checkNullBlank(hofRequestDto.getIntroduction()) || checkNullBlank(hofRequestDto.getCompany())) return true;
        return false;
    }

    public boolean checkNullBlank(String string) {
        if (string == null || string.isBlank()) return true;
        return false;
    }
}
