package com.dogvelopers.dogvelopers.controller.admin;

import com.dogvelopers.dogvelopers.dto.member.MemberRequestDto;
import com.dogvelopers.dogvelopers.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberService memberService;

    public void memberMvcAddObject(ModelAndView mvc) {
        mvc.addObject("memberList", memberService.findAll());
    }

    @GetMapping() // 처음 화면
    public ModelAndView setMember() {
        ModelAndView mvc = new ModelAndView("members/createMemberForm");
        mvc.addObject("member", new MemberRequestDto());
        memberMvcAddObject(mvc);
        return mvc;
    }

    @PostMapping(params = "cmd=register") // 등록
    public ModelAndView registerMember(
            @RequestPart(value = "file" , required = false) MultipartFile file ,
            MemberRequestDto memberRequestDto) {
        ModelAndView mvc = new ModelAndView("members/createMemberForm");
        memberService.save(file , memberRequestDto);
        mvc.addObject("member", new MemberRequestDto());
        memberMvcAddObject(mvc);
        return mvc; //등록화면으로 다시 넘어감
    }

    @PostMapping(params = "cmd=inquiry") // 조회
    public ModelAndView inquiryMember(@RequestParam("id") Long memberId) {
        ModelAndView mvc = new ModelAndView("members/createMemberForm");
        if (!memberService.existsById(memberId)) {
            mvc.addObject("member", new MemberRequestDto()); // 없으면 빈 객체로
        } else {
            mvc.addObject("member", memberService.findById(memberId)); // 있으면 채운채로
        }
        memberMvcAddObject(mvc);
        return mvc;
    }

    @PostMapping(params = "cmd=update") // 업데이트
    public ModelAndView updateMember(@RequestParam("id") Long memberId ,
                                     @RequestPart(value = "file" , required = false) MultipartFile file ,
                                     MemberRequestDto memberRequestDto) {
        ModelAndView mvc = new ModelAndView("members/createMemberForm");
        memberService.update(memberId, file , memberRequestDto); // update
        mvc.addObject("member", new MemberRequestDto());
        memberMvcAddObject(mvc);
        return mvc; // 다시 편집 가능하도록
    }


    @PostMapping(params = "cmd=delete") // 삭제
    public ModelAndView deleteMember(@RequestParam("id") Long memberId) {
        ModelAndView mvc = new ModelAndView("members/createMemberForm");
        memberService.delete(memberId);
        mvc.addObject("member", new MemberRequestDto());
        memberMvcAddObject(mvc);
        return mvc;
    }

}
