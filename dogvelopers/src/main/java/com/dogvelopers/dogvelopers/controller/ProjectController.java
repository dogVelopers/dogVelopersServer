package com.dogvelopers.dogvelopers.controller;

import com.dogvelopers.dogvelopers.controller.response.BasicResponse;
import com.dogvelopers.dogvelopers.controller.response.CommonResponse;
import com.dogvelopers.dogvelopers.dto.project.ProjectRequestDto;
import com.dogvelopers.dogvelopers.dto.project.ProjectResponseDto;
import com.dogvelopers.dogvelopers.dto.project.ProjectSaveRequestDto;
import com.dogvelopers.dogvelopers.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> findAll() {
        return ResponseEntity.ok()
                .body(projectService.findAll());
    }

    @GetMapping("{postId}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable("postId") Long id) {
        return ResponseEntity.ok()
                .body(new CommonResponse<>(projectService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<? extends BasicResponse> save(@RequestBody ProjectSaveRequestDto requestDto) {
        Long saveId = projectService.save(requestDto);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(projectService.findById(saveId)));
    }

    @PutMapping("{projectId}")
    public ResponseEntity<? extends BasicResponse> update(@PathVariable("projectId") Long id, @RequestBody ProjectSaveRequestDto updateDto) {
        Long projectId = projectService.update(id, updateDto);
        return ResponseEntity.ok()
                .body(new CommonResponse<>(projectService.findById(id)));
    }

    @DeleteMapping("{projectId}")
    public ResponseEntity<? extends BasicResponse> delete(@PathVariable("projectId") Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }
}
