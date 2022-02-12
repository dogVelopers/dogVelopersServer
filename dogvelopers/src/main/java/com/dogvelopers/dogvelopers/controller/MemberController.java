package com.dogvelopers.dogvelopers.controller;

import com.dogvelopers.dogvelopers.dto.MemberRequestDto;
import com.dogvelopers.dogvelopers.dto.MemberResponseDto;
import com.dogvelopers.dogvelopers.entity.Member;
import com.dogvelopers.dogvelopers.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping()
    public ResponseEntity<MemberResponseDto> findAll(){ // member 전체 조록
        return new ResponseEntity(memberService.findAll() , HttpStatus.OK);
    }

    @PostMapping("register") // member 등록
    public ResponseEntity<MemberResponseDto> save(MemberRequestDto memberRequestDto){
        return ResponseEntity.ok(memberService.save(memberRequestDto));
    }
}
