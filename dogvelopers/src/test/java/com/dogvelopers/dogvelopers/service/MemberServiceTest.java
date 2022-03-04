package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.dto.member.MemberRequestDto;
import com.dogvelopers.dogvelopers.dto.member.MemberResponseDto;
import com.dogvelopers.dogvelopers.handler.CustomException;
import com.dogvelopers.dogvelopers.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("dto를 받아 저장하고 Member를 조회")
    void save() throws IOException {
        // given
        MemberRequestDto dto = generateMemberRequestDto("김재연");

        // when
        MemberResponseDto saveDto = memberService.save(generatedFile() , dto);
        MemberResponseDto findDto = memberService.findById(saveDto.getId());

        // then
        assertAll(
                () -> assertEquals(findDto.getId(), saveDto.getId()),
                () -> assertEquals(findDto.getName(), dto.getName()),
                () -> assertEquals(findDto.getMajor(), dto.getMajor()),
                () -> assertEquals(findDto.getBirthDay(), dto.getBirthDay()),
                () -> assertEquals(findDto.getGeneration(), dto.getGeneration()),
                () -> assertEquals(getFileExtension(findDto.getImageUrl()) , getFileExtension(saveDto.getImageUrl()))
        );
    }

    @Test
    @DisplayName("Member 갱신하고 조회")
    void update() throws IOException{

        //given
        MemberRequestDto dto = generateMemberRequestDto("김재연");

        String afterName = "감나무" , afterMajor = "정통" , afterStudentId = "201733001";
        Long afterGeneration = 16L;
        LocalDate afterBirthDay = LocalDate.of(1998 , 8 , 29);

        MemberRequestDto updateDto = MemberRequestDto.builder()
                .name(afterName)
                .major(afterMajor)
                .birthDay(afterBirthDay)
                .generation(afterGeneration)
                .studentId(afterStudentId)
                .build();

        MemberResponseDto beforeDto = memberService.save(generatedFile() , dto);
        memberService.update(beforeDto.getId() , generatedFile() , updateDto); // 파일은 안 넘기고 그냥 해봄

        MemberResponseDto findDto = memberService.findById(beforeDto.getId());

        assertAll(
                () -> assertEquals(findDto.getId(), beforeDto.getId()),
                () -> assertEquals(findDto.getName(), afterName),
                () -> assertEquals(findDto.getMajor(), afterMajor),
                () -> assertEquals(findDto.getBirthDay(), afterBirthDay),
                () -> assertEquals(findDto.getGeneration(), afterGeneration),
                () -> assertEquals(getFileExtension(findDto.getImageUrl()) , getFileExtension(beforeDto.getImageUrl()))
        );
    }

    @Test
    @DisplayName("존재하지 않는 Member를 수정하여 에러를 던짐")
    void update_error() {
        // given
        MemberRequestDto updateDto = generateMemberRequestDto("김재연");

        // when, then
        assertThrows(CustomException.class, () ->
                memberService.update(1L, generatedFile() , updateDto));
    }

    @Test
    @DisplayName("Member 삭제")
    void delete() throws IOException{
        // given
        MemberRequestDto memberRequestDto = generateMemberRequestDto("김재연");
        MemberResponseDto memberResponseDto = memberService.save(generatedFile() ,memberRequestDto);

        // when
        memberService.delete(memberResponseDto.getId());

        // then
        assertEquals(memberRepository.existsById(memberResponseDto.getId()), false);
    }

    @Test
    @DisplayName("없는 Member 삭제")
    void delete_error() throws IOException{
        // given
        MemberRequestDto memberRequestDto = generateMemberRequestDto("김재연");
        MemberResponseDto memberResponseDto = memberService.save(generatedFile() ,memberRequestDto);

        // when
        memberService.delete(memberResponseDto.getId());

        // then
        assertThrows(CustomException.class, () ->
                memberService.delete(memberResponseDto.getId()));
    }

    static private MemberRequestDto generateMemberRequestDto(String s){

        // 들어오는 문자열로 만들어서 반환 , image 는 그냥 null로 유지
        return MemberRequestDto.builder()
                .name(s)
                .major("소프")
                .studentId("201733009")
                .generation(15L)
                .birthDay(LocalDate.of(1998 , 6 , 5))
                .build();
    }

    static private String generatedFilePath(){
        return "/Users/jaeyeonkim/Downloads/KakaoTalk_Photo_2022-01-29-22-04-01.jpeg"; // 내가 가지고 있는 kakao talk 사진을 이용해서 multipartfile 생성
    }

    static private MockMultipartFile generatedFile() throws IOException {
        Resource fileResource = new ClassPathResource(generatedFilePath());

        return new MockMultipartFile("file",
                "KakaoTalk_Photo_2022-01-29-22-04-01.jpeg",
                "image/jpeg",
                new FileInputStream(generatedFilePath()));
    }
    static private String getFileExtension(String s){
        return s.substring(s.lastIndexOf("."));
    }
}
