package com.dogvelopers.dogvelopers.controller;

import com.dogvelopers.dogvelopers.dto.HofRequestDto;
import com.dogvelopers.dogvelopers.dto.HofResponseDto;
import com.dogvelopers.dogvelopers.service.HofService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("hof")
public class HofController {

    private final HofService hofService;

    @GetMapping()
    public ResponseEntity<HofResponseDto> findAll(){ // 명예의 전당 전부 조회
        return new ResponseEntity(hofService.findAll() , HttpStatus.OK);
    }

    @PostMapping("register") // 명예의 전당 등록
    public ResponseEntity<HofResponseDto> register(HofRequestDto hofRequestDto){
        return ResponseEntity.ok(hofService.save(hofRequestDto));
    }
}

