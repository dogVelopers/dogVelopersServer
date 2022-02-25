package com.dogvelopers.dogvelopers.controller.admin;

import com.dogvelopers.dogvelopers.dto.project.ProjectRequestDto;
import com.dogvelopers.dogvelopers.dto.project.ProjectSaveRequestDto;
import com.dogvelopers.dogvelopers.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/projects")
@RequiredArgsConstructor
public class AdminProjectController {

    private final ProjectService projectService;

    public void projectMvcAddObject(ModelAndView mvc) {
        mvc.addObject("projectList", projectService.findAll());
    }

    public ProjectSaveRequestDto createProjectSaveRequestDto(ProjectRequestDto projectRequestDto) {
        return ProjectSaveRequestDto.builder()
                .name(projectRequestDto.getName())
                .description(projectRequestDto.getDescription())
                .startDate(projectRequestDto.getStartDate())
                .endDate(projectRequestDto.getEndDate())
                .build();
    }

    @GetMapping() // 처음 화면
    public ModelAndView setProject() {
        ModelAndView mvc = new ModelAndView("projects/createProjectForm");
        mvc.addObject("project", new ProjectRequestDto());
        projectMvcAddObject(mvc);
        return mvc;
    }

    @PostMapping(params = "cmd=register") // 등록
    public ModelAndView registerProject(ProjectRequestDto projectRequestDto) {
        ProjectSaveRequestDto projectSaveRequestDto = createProjectSaveRequestDto(projectRequestDto);
        ModelAndView mvc = new ModelAndView("projects/createProjectForm");
        projectService.save(projectSaveRequestDto);
        mvc.addObject("project", new ProjectRequestDto());
        projectMvcAddObject(mvc);
        return mvc; //등록화면으로 다시 넘어감
    }

    @PostMapping(params = "cmd=inquiry") // 조회
    public ModelAndView inquiryProject(@RequestParam("id") Long projectId) {
        ModelAndView mvc = new ModelAndView("projects/createProjectForm");
        if (!projectService.existsById(projectId)) mvc.addObject("project", new ProjectRequestDto()); // 없으면 빈 객체로
        else mvc.addObject("project", projectService.findById(projectId)); // 있으면 채운채로
        projectMvcAddObject(mvc);
        return mvc;
    }

    @PostMapping(params = "cmd=update") // 업데이트
    public ModelAndView updateProject(@RequestParam("id") Long projectId, ProjectRequestDto projectRequestDto) {
        ProjectSaveRequestDto projectSaveRequestDto = createProjectSaveRequestDto(projectRequestDto);
        ModelAndView mvc = new ModelAndView("projects/createProjectForm");
        projectService.update(projectId, projectSaveRequestDto); // update
        mvc.addObject("project", new ProjectRequestDto());
        projectMvcAddObject(mvc);
        return mvc; // 다시 편집 가능하도록
    }


    @PostMapping(params = "cmd=delete") // 삭제
    public ModelAndView deleteProject(@RequestParam("id") Long projectId) {
        ModelAndView mvc = new ModelAndView("projects/createProjectForm");
        projectService.delete(projectId);
        mvc.addObject("project", new ProjectRequestDto());
        projectMvcAddObject(mvc);
        return mvc;
    }
}
