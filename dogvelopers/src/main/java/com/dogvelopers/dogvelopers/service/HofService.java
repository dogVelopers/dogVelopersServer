package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.dto.hof.HofRequestDto;
import com.dogvelopers.dogvelopers.dto.hof.HofResponseDto;
import com.dogvelopers.dogvelopers.entity.Hof;
import com.dogvelopers.dogvelopers.entity.Member;
import com.dogvelopers.dogvelopers.handler.CustomException;
import com.dogvelopers.dogvelopers.repository.HofRepository;
import com.dogvelopers.dogvelopers.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<HofResponseDto> findAllByOrderByComponentDirection(Long offset , Long page , String sortBy , String direction){
        List<Hof> hofs; // 받은 결과를 채울 것

        // sortBy , direction 중 하나만 null 혹은 blank 인 경우
        if((checkNullBlank(sortBy) && !checkNullBlank(direction)) || (!checkNullBlank(sortBy) && checkNullBlank(direction)))
            throw new CustomException(BAD_REQUEST_INFO);

        if((offset == null && !(page == null)) || (!(offset == null) && page == null))
            throw new CustomException(BAD_REQUEST_INFO);

        if(checkNullBlank(sortBy) && checkNullBlank(direction)){ // 하나라도 비어있으면 실행 그냥 pagination만
            if(offset == null && page == null){ // pagination 안된 그냥 all
                hofs = hofRepository.findAll();
            }else{
                hofs = hofRepository.findAll(PageRequest.of(page.intValue() , offset.intValue())).getContent();
            }
        }
        else{ // 솔트가 다 된다.
            if(offset == null && page == null){ // pagination 적용 x
                if(sortBy.equals("generation")){
                    if(direction.equals("asc")){
                        hofs = hofRepository.findAllByOrderByGenerationAsc();
                    }else{
                        hofs = hofRepository.findAllByOrderByGenerationDesc();
                    }
                }else{
                    if(direction.equals("asc")){
                        hofs = hofRepository.findAllByOrderByStudentIdAsc();
                    }else{
                        hofs = hofRepository.findAllByOrderByStudentIdDesc();
                    }
                }
            }
            else{ // pagination 적용 o
                PageRequest pageRequest = PageRequest.of(page.intValue() , offset.intValue());
                if(sortBy.equals("generation")){
                    if(direction.equals("asc")){
                        hofs = hofRepository.findAllByOrderByGenerationAsc(pageRequest).getContent();
                    }else{
                        hofs = hofRepository.findAllByOrderByGenerationDesc(pageRequest).getContent();
                    }
                }else{
                    if(direction.equals("asc")){
                        hofs = hofRepository.findAllByOrderByStudentIdAsc(pageRequest).getContent();
                    }else{
                        hofs = hofRepository.findAllByOrderByStudentIdDesc(pageRequest).getContent();
                    }
                }
            }
        }

        return hofs.stream()
                .map(hof -> new HofResponseDto(hof))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<HofResponseDto> findAll() {
        return hofRepository.findAllByOrderByGenerationDesc().stream()
                .map(hof -> new HofResponseDto(hof))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<HofResponseDto> findByGeneration(Long generation) { // 기수 별로 조회
        return hofRepository.findByGenerationOrderByGenerationDesc(generation).stream()
                .map(hof -> new HofResponseDto(hof))
                .collect(Collectors.toList());
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
