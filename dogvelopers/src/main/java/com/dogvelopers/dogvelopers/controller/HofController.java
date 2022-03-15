package com.dogvelopers.dogvelopers.controller;

import com.dogvelopers.dogvelopers.dto.hof.HofRequestDto;
import com.dogvelopers.dogvelopers.dto.hof.HofResponseDto;
import com.dogvelopers.dogvelopers.dto.member.MemberResponseDto;
import com.dogvelopers.dogvelopers.service.HofService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("hofs")
public class HofController {

    private final HofService hofService;

    @GetMapping() // 명예의 전당 모두 조회
    public ResponseEntity<HofResponseDto> findAll(Long offset ,
                                                  Long page ,
                                                  @RequestParam(value = "sortby" , required = false) String sortBy,
                                                  String direction) { // 명예의 전당 전부 조회
        return new ResponseEntity(hofService.findAllByOrderByComponentDirection(offset , page , sortBy , direction), HttpStatus.OK);
    }

    @GetMapping("{generation}") // 명예의 전당 기수별로 조회
    public ResponseEntity<HofResponseDto> findByGeneration(@PathVariable("generation") Long generation) {
        return new ResponseEntity(hofService.findByGeneration(generation), HttpStatus.OK);
    }

    @PostMapping() // 명예의 전당 등록
    public ResponseEntity<HofResponseDto> save(HofRequestDto hofRequestDto) {
        return ResponseEntity.ok(hofService.save(hofRequestDto));
    }

    @PutMapping("{hofId}") // 명예의 전당 회원 수정
    public ResponseEntity<MemberResponseDto> update(@PathVariable("hofId") Long id, HofRequestDto hofRequestDto) {
        return new ResponseEntity(hofService.update(id, hofRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("{hofId}") // 명예의 전당 회원 삭제
    public ResponseEntity delete(@PathVariable("hofId") Long id) {
        hofService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }
}

