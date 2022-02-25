package com.dogvelopers.dogvelopers.controller.admin;

import com.dogvelopers.dogvelopers.dto.hof.HofAdminDto;
import com.dogvelopers.dogvelopers.dto.hof.HofRequestDto;
import com.dogvelopers.dogvelopers.dto.hof.HofResponseDto;
import com.dogvelopers.dogvelopers.service.HofService;
import com.dogvelopers.dogvelopers.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/hofs")
@RequiredArgsConstructor
public class AdminHofController {

    private final HofService hofService;
    private final MemberService memberService;

    public List<HofAdminDto> createHofAdminDtoList(List<HofResponseDto> hofs) {
        List<HofAdminDto> result = new ArrayList<>();
        for (HofResponseDto hof : hofs) {
            result.add(createHofAdminDto(hof));
        }
        return result;
    }

    public HofAdminDto createHofAdminDto(HofResponseDto hof) {
        Long memberId = hof.getMember().getId();
        return HofAdminDto.builder()
                .id(hof.getId())
                .memberId(memberId)
                .company(hof.getCompany())
                .introduction(hof.getIntroduction())
                .build();
    }

    public void hofMvcAddObject(ModelAndView mvc) {
        mvc.addObject("hofList", createHofAdminDtoList(hofService.findAll()));
        mvc.addObject("memberList", memberService.findAll());
    }

    @GetMapping() // 처음 화면
    public ModelAndView setHof() {
        ModelAndView mvc = new ModelAndView("hofs/createHofForm");
        mvc.addObject("hof", new HofAdminDto());
        hofMvcAddObject(mvc);
        return mvc;
    }

    @PostMapping(params = "cmd=register") // 등록
    public ModelAndView registerHof(HofRequestDto hofRequestDto) {
        ModelAndView mvc = new ModelAndView("hofs/createHofForm");
        hofService.save(hofRequestDto);
        mvc.addObject("hof", new HofAdminDto());
        hofMvcAddObject(mvc);
        return mvc; //등록화면으로 다시 넘어감
    }

    @PostMapping(params = "cmd=inquiry") // 조회
    public ModelAndView inquiryHof(@RequestParam("id") Long hofId) {
        ModelAndView mvc = new ModelAndView("hofs/createHofForm");
        if (!hofService.existsById(hofId)) mvc.addObject("hof", new HofAdminDto()); // 없으면 빈 객체로
        else mvc.addObject("hof", createHofAdminDto(hofService.findById(hofId))); // 있으면 채운채로
        hofMvcAddObject(mvc);
        return mvc;
    }

    @PostMapping(params = "cmd=update") // 업데이트
    public ModelAndView updateHof(@RequestParam("id") Long hofId, HofRequestDto hofRequestDto) {
        ModelAndView mvc = new ModelAndView("hofs/createHofForm");
        hofService.update(hofId , hofRequestDto); // update
        mvc.addObject("hof", new HofAdminDto());
        hofMvcAddObject(mvc);
        return mvc; // 다시 편집 가능하도록
    }


    @PostMapping(params = "cmd=delete") // 삭제
    public ModelAndView deleteHof(@RequestParam("id") Long hofId) {
        ModelAndView mvc = new ModelAndView("hofs/createHofForm");
        hofService.delete(hofId);
        mvc.addObject("hof", new HofRequestDto());
        hofMvcAddObject(mvc);
        return mvc;
    }
}
