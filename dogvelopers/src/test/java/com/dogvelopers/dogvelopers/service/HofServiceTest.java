package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.dto.hof.HofRequestDto;
import com.dogvelopers.dogvelopers.dto.hof.HofResponseDto;
import com.dogvelopers.dogvelopers.dto.member.MemberRequestDto;
import com.dogvelopers.dogvelopers.dto.member.MemberResponseDto;
import com.dogvelopers.dogvelopers.dto.project.ProjectResponseDto;
import com.dogvelopers.dogvelopers.dto.project.ProjectSaveRequestDto;
import com.dogvelopers.dogvelopers.entity.Hof;
import com.dogvelopers.dogvelopers.repository.HofRepository;
import com.dogvelopers.dogvelopers.repository.MemberRepository;
import com.dogvelopers.dogvelopers.repository.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class HofServiceTest {

    @Autowired
    HofService hofService;

    @Autowired
    HofRepository hofRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("dto를 받아 저장하고 hof를 조회")
    void save() {
        // given
        MemberResponseDto memberResponseDto = saveDto(generateMemberRequestDto());
        HofRequestDto dto = generateHofRequestDto(memberResponseDto.getId() , "김재연");

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

//    @Test
//    @DisplayName("Project 갱신하고 조회")
//    void update() {
//        ProjectSaveRequestDto projectSaveRequestDto = generateDto();
//
//        ProjectSaveRequestDto updateDto = ProjectSaveRequestDto.builder()
//                .name("수정")
//                .description("수정")
//                .startDate(LocalDate.of(2022, 01, 02))
//                .endDate(LocalDate.of(2022, 02, 02))
//                .build();
//
//        Long saveId = projectService.save(projectSaveRequestDto);
//        Long updateId = projectService.update(saveId, updateDto);
//
//        ProjectResponseDto findUpdateDto = projectService.findById(updateId);
//
//        assertAll(
//                () -> assertEquals(findUpdateDto.getId(), saveId),
//                () -> assertEquals(findUpdateDto.getName(), updateDto.getName()),
//                () -> assertEquals(findUpdateDto.getDescription(), updateDto.getDescription()),
//                () -> assertEquals(findUpdateDto.getStartDate(), updateDto.getStartDate()),
//                () -> assertEquals(findUpdateDto.getEndDate(), updateDto.getEndDate())
//        );
//    }
//
//    @Test
//    @DisplayName("존재하지 않는 게시물을 수정하여 에러를 던짐")
//    void update_error() {
//        // given
//        ProjectSaveRequestDto updateDto = generateDto();
//
//        // when, then
//        assertThrows(EntityNotFoundException.class, () ->
//                projectService.update(1L, updateDto));
//    }
//
//    @Test
//    @DisplayName("Project 삭제")
//    void delete() {
//        // given
//        ProjectSaveRequestDto projectSaveRequestDto = generateDto();
//        Long saveId = projectService.save(projectSaveRequestDto);
//
//        // when
//        projectService.delete(saveId);
//
//        // then
//        assertEquals(projectRepository.findAll().size(), 0);
//    }
//
//    @Test
//    @DisplayName("없는 Project 삭제")
//    void delete_error() {
//        // given
//        ProjectSaveRequestDto deleteDto = generateDto();
//        Long saveId = projectService.save(deleteDto);
//
//        // when
//        projectService.delete(saveId);
//
//        // then
//        assertThrows(EntityNotFoundException.class, () ->
//                projectService.delete(saveId));
//    }

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
