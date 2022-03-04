package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.dto.hof.HofRequestDto;
import com.dogvelopers.dogvelopers.dto.hof.HofResponseDto;
import com.dogvelopers.dogvelopers.dto.member.MemberRequestDto;
import com.dogvelopers.dogvelopers.dto.member.MemberResponseDto;
import com.dogvelopers.dogvelopers.entity.Hof;
import com.dogvelopers.dogvelopers.handler.CustomException;
import com.dogvelopers.dogvelopers.repository.HofRepository;
import com.dogvelopers.dogvelopers.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class HofServiceTest {

    @Autowired
    HofService hofService;

    @Autowired
    HofRepository hofRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("dto를 받아 저장하고 Hof를 조회")
    void save() {
        // given
        MemberResponseDto memberResponseDto = saveDto(generateMemberRequestDto());
        HofRequestDto dto = generateHofRequestDto(memberResponseDto.getId() , "카카오");

        // when
        HofResponseDto hofResponseDto = hofService.save(dto); // dto를 저장하고 , 반환 받아서 , Id 로 검사
        Hof hof = hofRepository.findById(hofResponseDto.getId()).get();

        // then
        assertAll(
                () -> assertEquals(hof.getIntroduction(), dto.getIntroduction()),
                () -> assertEquals(hof.getCompany(), dto.getCompany()),
                () -> assertEquals(hof.getId(), hofResponseDto.getId()),
                () -> assertEquals(hof.getMember().getId(), memberResponseDto.getId())
        );
    }

    @Test
    @DisplayName("Hof를 갱신하고 조회")
    void update() {
        // given
        String beforeCompany = "카카오";
        MemberResponseDto memberResponseDto = saveDto(generateMemberRequestDto());
        HofRequestDto dto = generateHofRequestDto(memberResponseDto.getId() , beforeCompany);

        // when
        String afterCompany = "삼성" , afterIntroduction = "안녕하세요!";
        HofResponseDto before = hofService.save(dto); // save 먼저하고
        HofRequestDto updateDto = HofRequestDto.builder()
                .company(afterCompany)
                .introduction(afterIntroduction)
                .memberId(memberResponseDto.getId())
                .build(); // 갱신할 내용으로 HofRequestDto 선언

        HofResponseDto after = hofService.update(before.getId() , updateDto); // 업데이트

        assertAll(
                () -> assertEquals(after.getCompany(), afterCompany), // update 된 부분 조회
                () -> assertEquals(after.getIntroduction(), afterIntroduction),
                () -> assertEquals(before.getId(), after.getId()), // update 가 되었으면 , id는 같아야함
                () -> assertEquals(before.getMember().getId(), memberResponseDto.getId())
        );
    }

    @Test
    @DisplayName("존재하지 않는 Hof를 수정하여 에러를 던짐")
    void update_error() {
        // given
        MemberResponseDto memberResponseDto = saveDto(generateMemberRequestDto());
        HofRequestDto updateDto = generateHofRequestDto(memberResponseDto.getId() , "카카오");

        // when, then
        assertThrows(CustomException.class, () ->
                hofService.update(100L, updateDto));
    }

    @Test
    @DisplayName("Hof 삭제")
    void delete() {
        // given
        MemberResponseDto memberResponseDto = saveDto(generateMemberRequestDto());
        HofRequestDto hofRequestDto = generateHofRequestDto(memberResponseDto.getId() , "카카오");

        HofResponseDto hofResponseDto = hofService.save(hofRequestDto);

        // when
        hofService.delete(hofResponseDto.getId());

        // then
        assertEquals(hofRepository.existsById(hofResponseDto.getId()) , false);
    }

    @Test
    @DisplayName("없는 Hof 삭제")
    void delete_error() {
        // given
        MemberResponseDto memberResponseDto = saveDto(generateMemberRequestDto());
        HofRequestDto deleteDto = generateHofRequestDto(memberResponseDto.getId() , "카카오");

        HofResponseDto hofResponseDto = hofService.save(deleteDto);

        // when
        hofService.delete(hofResponseDto.getId());

        // then
        assertThrows(CustomException.class, () ->
                hofService.delete(hofResponseDto.getId()));
    }

    static private MemberRequestDto generateMemberRequestDto(){

        // 들어오는 문자열로 만들어서 반환 , image 는 그냥 null로 유지
        return MemberRequestDto.builder()
                .name("김재연")
                .major("소프")
                .studentId("201733009")
                .generation(15L)
                .birthDay(LocalDate.of(1998 , 6 , 5))
                .build();
    }

    static private HofRequestDto generateHofRequestDto(Long memberId , String s) {
        return HofRequestDto.builder()
                .memberId(memberId)
                .company(s)
                .introduction("안녕하세요")
                .build();
    }

    public MemberResponseDto saveDto(MemberRequestDto memberRequestDto){
        return new MemberResponseDto(memberRepository.save(memberRequestDto.toEntity()));
        // dto를 저장하면서 , ResponseDto 를 반환
    }
}
