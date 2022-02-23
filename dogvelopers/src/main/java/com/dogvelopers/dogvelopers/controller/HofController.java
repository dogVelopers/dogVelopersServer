package com.dogvelopers.dogvelopers.controller;

import com.dogvelopers.dogvelopers.dto.hof.HofRequestDto;
import com.dogvelopers.dogvelopers.dto.hof.HofResponseDto;
import com.dogvelopers.dogvelopers.dto.member.MemberResponseDto;
import com.dogvelopers.dogvelopers.service.HofService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("hofs")
public class HofController {

    private final HofService hofService;

    // 만들어야 할 것이 save , update , delete 는 되는 thymeleaf 페이지를 만들어야 할 것이다.
    @GetMapping("/new")
    public String setHof(){
        return "hofs/createHofForm";
    }

    @GetMapping() // 명예의 전당 모두 조회
    public @ResponseBody ResponseEntity<HofResponseDto> findAll(){ // 명예의 전당 전부 조회
        return new ResponseEntity(hofService.findAll() , HttpStatus.OK);
    }

    @GetMapping("{generation}") // 명예의 전당 기수별로 조회
    public @ResponseBody ResponseEntity<HofResponseDto> findByGeneration(@PathVariable("generation") Long generation){
        return new ResponseEntity(hofService.findByGeneration(generation) , HttpStatus.OK);
    }

    @PostMapping() // 명예의 전당 등록
    public @ResponseBody ResponseEntity<HofResponseDto> save(HofRequestDto hofRequestDto){
        return ResponseEntity.ok(hofService.save(hofRequestDto));
    }

    @PutMapping("{hofId}") // 명예의 전당 회원 수정
    public @ResponseBody ResponseEntity<MemberResponseDto> update(@PathVariable("hofId") Long id, HofRequestDto hofRequestDto) {
        return new ResponseEntity(hofService.update(id, hofRequestDto) , HttpStatus.OK);
    }

    @DeleteMapping("{hofId}") // 명예의 전당 회원 삭제
    public @ResponseBody ResponseEntity delete(@PathVariable("hofId") Long id) {
        hofService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }
}

