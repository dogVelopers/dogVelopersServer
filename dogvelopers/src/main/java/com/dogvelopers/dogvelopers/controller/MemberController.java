package com.dogvelopers.dogvelopers.controller;

import com.dogvelopers.dogvelopers.controller.response.BasicResponse;
import com.dogvelopers.dogvelopers.controller.response.CommonResponse;
import com.dogvelopers.dogvelopers.dto.member.MemberRequestDto;
import com.dogvelopers.dogvelopers.dto.member.MemberResponseDto;
import com.dogvelopers.dogvelopers.dto.project.ProjectSaveRequestDto;
import com.dogvelopers.dogvelopers.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("new")
    public String setMember(){
        return "members/createMemberForm";
    }

    @GetMapping()
    public @ResponseBody ResponseEntity<MemberResponseDto> findAll(){ // member 전체 조회
        return new ResponseEntity(memberService.findAll() , HttpStatus.OK);
    }

    @GetMapping("{generation}") // 기수로 검색
    public @ResponseBody ResponseEntity<MemberResponseDto> findByGeneration(@PathVariable("generation") Long generation){
        return new ResponseEntity(memberService.findByGeneration(generation) , HttpStatus.OK);
    }

    @PostMapping() // member 등록
    public @ResponseBody ResponseEntity<MemberResponseDto> save(MemberRequestDto memberRequestDto){
        return ResponseEntity.ok(memberService.save(memberRequestDto));
    }

    @PutMapping("{memberId}") // member 수정
    public @ResponseBody ResponseEntity<MemberResponseDto> update(@PathVariable("memberId") Long id, MemberRequestDto memberRequestDto) {
        return new ResponseEntity(memberService.update(id, memberRequestDto) , HttpStatus.OK);
    }

    @DeleteMapping("{memberId}") // member 삭제
    public @ResponseBody ResponseEntity delete(@PathVariable("memberId") Long id) {
        memberService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }
}
