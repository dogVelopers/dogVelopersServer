package com.dogvelopers.dogvelopers.controller;

import com.dogvelopers.dogvelopers.dto.member.MemberRequestDto;
import com.dogvelopers.dogvelopers.dto.member.MemberResponseDto;
import com.dogvelopers.dogvelopers.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping()
    public ResponseEntity<MemberResponseDto> findAll() { // member 전체 조회
        return new ResponseEntity(memberService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{generation}") // 기수로 검색
    public ResponseEntity<MemberResponseDto> findByGeneration(@PathVariable("generation") Long generation) {
        return new ResponseEntity(memberService.findByGeneration(generation), HttpStatus.OK);
    }

    @PostMapping() // member 등록
    public ResponseEntity<MemberResponseDto> save(
            @RequestPart(value = "file" , required = false) MultipartFile file, // 이것 역시 필수 x
            MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.save(file , memberRequestDto));
    }

    @PutMapping("{memberId}") // member 수정
    public ResponseEntity<MemberResponseDto> update(@PathVariable("memberId") Long id,
                                                    @RequestPart(value = "file" , required = false) MultipartFile file, // 이것 역시 필수 x
                                                    MemberRequestDto memberRequestDto) {
        return new ResponseEntity(memberService.update(id, file , memberRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("{memberId}") // member 삭제
    public ResponseEntity delete(@PathVariable("memberId") Long id) {
        memberService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }
}
