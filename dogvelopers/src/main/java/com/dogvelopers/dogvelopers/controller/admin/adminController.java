package com.dogvelopers.dogvelopers.controller.admin;

import com.dogvelopers.dogvelopers.dto.hof.HofAdminDto;
import com.dogvelopers.dogvelopers.dto.hof.HofRequestDto;
import com.dogvelopers.dogvelopers.dto.member.MemberRequestDto;
import com.dogvelopers.dogvelopers.dto.project.ProjectRequestDto;
import com.dogvelopers.dogvelopers.dto.project.ProjectSaveRequestDto;
import com.dogvelopers.dogvelopers.entity.Hof;
import com.dogvelopers.dogvelopers.repository.HofRepository;
import com.dogvelopers.dogvelopers.repository.MemberRepository;
import com.dogvelopers.dogvelopers.repository.ProjectRepository;
import com.dogvelopers.dogvelopers.service.HofService;
import com.dogvelopers.dogvelopers.service.MemberService;
import com.dogvelopers.dogvelopers.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class adminController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    private final HofService hofService;
    private final HofRepository hofRepository;

    private final ProjectService projectService;
    private final ProjectRepository projectRepository;

    public void memberMvcAddObject(ModelAndView mvc){
        mvc.addObject("memberList" , memberRepository.findAll());
    }
    @GetMapping("/members") // 처음 화면
    public ModelAndView setMember(){
        ModelAndView mvc = new ModelAndView("members/createMemberForm");
        mvc.addObject("member" , new MemberRequestDto());
        memberMvcAddObject(mvc);
        return mvc;
    }

    @PostMapping(value = "/members" , params = "cmd=register") // 등록
    public ModelAndView registerMember(MemberRequestDto memberRequestDto){
        ModelAndView mvc = new ModelAndView("members/createMemberForm");
        memberService.save(memberRequestDto);
        mvc.addObject("member" , new MemberRequestDto());
        memberMvcAddObject(mvc);
        return mvc; //등록화면으로 다시 넘어감
    }

    @PostMapping(value = "/members" , params = "cmd=inquiry") // 조회
    public ModelAndView inquiryMember(@RequestParam("id") Long memberId){
        ModelAndView mvc = new ModelAndView("members/createMemberForm");
        if(!memberRepository.existsById(memberId)) mvc.addObject("member" , new MemberRequestDto()); // 없으면 빈 객체로
        else mvc.addObject("member" , memberRepository.findById(memberId).get()); // 있으면 채운채로
        memberMvcAddObject(mvc);
        return mvc;
    }

    @PostMapping(value = "/members" , params = "cmd=update") // 업데이트
    public ModelAndView updateMember(@RequestParam("id") Long memberId , MemberRequestDto memberRequestDto){
        ModelAndView mvc = new ModelAndView("members/createMemberForm");
        memberService.update(memberId , memberRequestDto); // update
        mvc.addObject("member" , new MemberRequestDto());
        memberMvcAddObject(mvc);
        return mvc; // 다시 편집 가능하도록
    }


    @PostMapping(value = "/members" , params = "cmd=delete") // 삭제
    public ModelAndView deleteMember(@RequestParam("id") Long memberId){
        ModelAndView mvc = new ModelAndView("members/createMemberForm");
        memberService.delete(memberId);
        mvc.addObject("member" , new MemberRequestDto());
        memberMvcAddObject(mvc);
        return mvc;
    }

    public List<HofAdminDto> createHofAdminDtoList(List<Hof> hofs){
        List<HofAdminDto> result = new ArrayList<>();
        for(Hof hof : hofs){
            result.add(createHofAdminDto(hof));
        }
        return result;
    }

    public HofAdminDto createHofAdminDto(Hof hof){
        Long memberId = hof.getMember().getId();
        return HofAdminDto.builder()
                .id(hof.getId())
                .memberId(memberId)
                .company(hof.getCompany())
                .introduction(hof.getIntroduction())
                .build();
    }

    public void hofMvcAddObject(ModelAndView mvc){
        mvc.addObject("hofList" , createHofAdminDtoList(hofRepository.findAll()));
        mvc.addObject("memberList" , memberRepository.findAll());
    }

    @GetMapping("/hofs") // 처음 화면
    public ModelAndView setHof(){
        ModelAndView mvc = new ModelAndView("hofs/createHofForm");
        mvc.addObject("hof" , new HofAdminDto());
        hofMvcAddObject(mvc);
        return mvc;
    }

    @PostMapping(value = "/hofs" , params = "cmd=register") // 등록
    public ModelAndView registerHof(HofRequestDto hofRequestDto){
        ModelAndView mvc = new ModelAndView("hofs/createHofForm");
        hofService.save(hofRequestDto);
        mvc.addObject("hof" , new HofAdminDto());
        hofMvcAddObject(mvc);
        return mvc; //등록화면으로 다시 넘어감
    }

    @PostMapping(value = "/hofs" , params = "cmd=inquiry") // 조회
    public ModelAndView inquiryHof(@RequestParam("id") Long hofId){
        ModelAndView mvc = new ModelAndView("hofs/createHofForm");
        if(!hofRepository.existsById(hofId)) mvc.addObject("hof" , new HofAdminDto()); // 없으면 빈 객체로
        else mvc.addObject("hof" , createHofAdminDto(hofRepository.findById(hofId).get())); // 있으면 채운채로
        hofMvcAddObject(mvc);
        return mvc;
    }

    @PostMapping(value = "/hofs" , params = "cmd=update") // 업데이트
    public ModelAndView updateHof(@RequestParam("id") Long hofId , HofRequestDto hofRequestDto){
        ModelAndView mvc = new ModelAndView("hofs/createHofForm");
        hofService.update(hofId , hofRequestDto); // update
        mvc.addObject("hof" , new HofAdminDto());
        hofMvcAddObject(mvc);
        return mvc; // 다시 편집 가능하도록
    }


    @PostMapping(value = "/hofs" , params = "cmd=delete") // 삭제
    public ModelAndView deleteHof(@RequestParam("id") Long hofId){
        ModelAndView mvc = new ModelAndView("hofs/createHofForm");
        hofService.delete(hofId);
        mvc.addObject("hof" , new HofRequestDto());
        hofMvcAddObject(mvc);
        return mvc;
    }

    public ProjectSaveRequestDto createProjectSaveRequestDto(ProjectRequestDto projectRequestDto){
        return ProjectSaveRequestDto.builder()
                .name(projectRequestDto.getName())
                .description(projectRequestDto.getDescription())
                .startDate(projectRequestDto.getStartDate())
                .endDate(projectRequestDto.getEndDate())
                .build();
    }

    public void projectMvcAddObject(ModelAndView mvc){
        mvc.addObject("projectList" , projectRepository.findAll());
    }

    @GetMapping("/projects") // 처음 화면
    public ModelAndView setProject(){
        ModelAndView mvc = new ModelAndView("projects/createProjectForm");
        mvc.addObject("project" , new ProjectRequestDto());
        projectMvcAddObject(mvc);
        return mvc;
    }

    @PostMapping(value = "/projects" , params = "cmd=register") // 등록
    public ModelAndView registerProject(ProjectRequestDto projectRequestDto){
        ProjectSaveRequestDto projectSaveRequestDto = createProjectSaveRequestDto(projectRequestDto);
        System.out.println(projectSaveRequestDto.getDescription() + " " + projectSaveRequestDto.getStartDate() + " " + projectSaveRequestDto.getEndDate());
        ModelAndView mvc = new ModelAndView("projects/createProjectForm");
        projectService.save(projectSaveRequestDto);
        mvc.addObject("project" , new ProjectRequestDto());
        projectMvcAddObject(mvc);
        return mvc; //등록화면으로 다시 넘어감
    }

    @PostMapping(value = "/projects" , params = "cmd=inquiry") // 조회
    public ModelAndView inquiryProject(@RequestParam("id") Long projectId){
        ModelAndView mvc = new ModelAndView("projects/createProjectForm");
        if(!projectRepository.existsById(projectId)) mvc.addObject("project" , new ProjectRequestDto()); // 없으면 빈 객체로
        else mvc.addObject("project" , projectRepository.findById(projectId).get()); // 있으면 채운채로
        projectMvcAddObject(mvc);
        return mvc;
    }

    @PostMapping(value = "/projects" , params = "cmd=update") // 업데이트
    public ModelAndView updateProject(@RequestParam("id") Long projectId , ProjectRequestDto projectRequestDto){
        ProjectSaveRequestDto projectSaveRequestDto = createProjectSaveRequestDto(projectRequestDto);
        ModelAndView mvc = new ModelAndView("projects/createProjectForm");
        projectService.update(projectId , projectSaveRequestDto); // update
        mvc.addObject("project" , new ProjectRequestDto());
        projectMvcAddObject(mvc);
        return mvc; // 다시 편집 가능하도록
    }


    @PostMapping(value = "/projects" , params = "cmd=delete") // 삭제
    public ModelAndView deleteProject(@RequestParam("id") Long projectId){
        ModelAndView mvc = new ModelAndView("projects/createProjectForm");
        projectService.delete(projectId);
        mvc.addObject("project" , new ProjectRequestDto());
        projectMvcAddObject(mvc);
        return mvc;
    }
}
