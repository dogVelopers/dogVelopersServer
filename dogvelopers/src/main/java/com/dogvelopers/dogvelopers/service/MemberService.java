package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.dto.member.MemberRequestDto;
import com.dogvelopers.dogvelopers.dto.member.MemberResponseDto;
import com.dogvelopers.dogvelopers.entity.Hof;
import com.dogvelopers.dogvelopers.entity.Member;
import com.dogvelopers.dogvelopers.handler.CustomException;
import com.dogvelopers.dogvelopers.repository.MemberRepository;
import com.dogvelopers.dogvelopers.service.image.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.dogvelopers.dogvelopers.enumType.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final FileUploadService fileUploadService;

    @Transactional(rollbackFor = Exception.class)
    public MemberResponseDto save(MultipartFile file , MemberRequestDto memberRequestDto) {

        // image 가 비었을 때 예외 발생
        if (file.isEmpty()) throw new CustomException(NOT_FOUND_IMAGE_FILE);

        // 생일이 잘못 되어 있고 , 이름 , 학번 , 전공 입력이 안되어있으면
        if (exceptionCheck(memberRequestDto)) {
            throw new CustomException(BAD_REQUEST_INFO); // exception 처리
        }

        // s3 에다가 image 파일 저장하고 , 받은 url 을 저장
        memberRequestDto.setImageUrl(fileUploadService.uploadImage(file));

        return new MemberResponseDto(memberRepository.save(memberRequestDto.toEntity()));
    }

    @Transactional
    public List<MemberResponseDto> findAllByOrderByComponentDirection(Long offset , Long page , String sortBy , String direction) { // 기수의 역순으로 반환되게 끔 설정
        List<Member> members;

        // sortBy , direction 중 하나만 null 혹은 blank 인 경우
        if((checkNullBlank(sortBy) && !checkNullBlank(direction)) || (!checkNullBlank(sortBy) && checkNullBlank(direction)))
            throw new CustomException(BAD_REQUEST_INFO);

        if((offset == null && !(page == null)) || (!(offset == null) && page == null))
            throw new CustomException(BAD_REQUEST_INFO);

        if(checkNullBlank(sortBy) && checkNullBlank(direction)){ // 하나라도 비어있으면 실행 그냥 pagination만
            if(offset == null && page == null){ // pagination 안된 그냥 all
                members = memberRepository.findAllByOrderByGenerationDesc();
            }else{
                members = memberRepository.findAllByOrderByGenerationDesc(PageRequest.of(page.intValue() , offset.intValue())).getContent();
            }
        }
        else{ // 솔트가 다 된다.
            if(offset == null && page == null){ // pagination 적용 x
                if(sortBy.equals("generation")){
                    if(direction.equals("asc")){
                        members = memberRepository.findAllByOrderByGenerationAsc();
                    }else{
                        members = memberRepository.findAllByOrderByGenerationDesc();
                    }
                }else{
                    if(direction.equals("asc")){
                        members = memberRepository.findAllByOrderByStudentIdAsc();
                    }else{
                        members = memberRepository.findAllByOrderByStudentIdDesc();
                    }
                }
            }
            else{ // pagination 적용 o
                PageRequest pageRequest = PageRequest.of(page.intValue() , offset.intValue());
                if(sortBy.equals("generation")){
                    if(direction.equals("asc")){
                        members = memberRepository.findAllByOrderByGenerationAsc(pageRequest).getContent();
                    }else{
                        members = memberRepository.findAllByOrderByGenerationDesc(pageRequest).getContent();
                    }
                }else{
                    if(direction.equals("asc")){
                        members = memberRepository.findAllByOrderByStudentIdAsc(pageRequest).getContent();
                    }else{
                        members = memberRepository.findAllByOrderByStudentIdDesc(pageRequest).getContent();
                    }
                }
            }
        }

        return members.stream()
                .map(member -> new MemberResponseDto(member))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<MemberResponseDto> findAll(){
        return memberRepository.findAllByOrderByGenerationDesc().stream()
                .map(member -> new MemberResponseDto(member))
                .collect(Collectors.toList());
    }

    @Transactional
    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> {throw new CustomException(NOT_FOUND_INFO);}
        );
        return new MemberResponseDto(member);
    }

    @Transactional
    public List<MemberResponseDto> findByGeneration(Long generation) {
        return memberRepository.findByGenerationOrderByGenerationDesc(generation).stream()
                .map(member -> new MemberResponseDto(member))
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public MemberResponseDto update(Long id, MultipartFile file , MemberRequestDto memberRequestDto) {

        // id 를 받아서 , 해당 객체를 뽑아서 , 해당 id 로 레코드의 내용을 바꾼다.
        if (exceptionCheck(memberRequestDto)) throw new CustomException(BAD_REQUEST_INFO);

        Member member = memberRepository.findById(id).orElseThrow(
                () -> {throw new CustomException(NOT_FOUND_INFO);}
        );

        // 파일이 비어있으면 그대로 , 아니면 s3 에 저장하고 url 가져옴
        memberRequestDto.setImageUrl(file.isEmpty() ? member.getImageUrl() : fileUploadService.uploadImage(file));
        member.updateMember(memberRequestDto.toEntity());

        return new MemberResponseDto(memberRepository.save(member));
    }

    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> {throw new CustomException(NOT_FOUND_INFO);}
        );
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
                || checkNullBlank(memberRequestDto.getMajor())
                || memberRequestDto == null)
            return true; // exception 처리
        return false; // 예외 안 걸림
    }

    public boolean checkNullBlank(String string) {
        if (string == null || string.isBlank()) return true;
        return false;
    }

}
