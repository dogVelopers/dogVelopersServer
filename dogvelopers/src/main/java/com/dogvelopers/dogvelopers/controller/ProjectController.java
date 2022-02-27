package com.dogvelopers.dogvelopers.controller;

import com.dogvelopers.dogvelopers.controller.response.BasicResponse;
import com.dogvelopers.dogvelopers.controller.response.CommonResponse;
import com.dogvelopers.dogvelopers.controller.response.ListResponse;
import com.dogvelopers.dogvelopers.dto.project.ProjectSaveRequestDto;
import com.dogvelopers.dogvelopers.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RestController
@RequestMapping("projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<? extends BasicResponse> findAll(Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ListResponse<>(projectService.findAll(pageable)));
    }

    @GetMapping("{postId}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable("postId") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CommonResponse<>(projectService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<? extends BasicResponse> save(@Valid @RequestBody ProjectSaveRequestDto requestDto) {
        Long saveId = projectService.save(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CommonResponse<>(projectService.findById(saveId)));
    }

    @PutMapping("{projectId}")
    public ResponseEntity<? extends BasicResponse> update(@PathVariable("projectId") Long id, @Valid @RequestBody ProjectSaveRequestDto updateDto) {
        Long projectId = projectService.update(id, updateDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CommonResponse<>(projectService.findById(projectId)));
    }

    @DeleteMapping("{projectId}")
    public ResponseEntity<? extends BasicResponse> delete(@PathVariable("projectId") Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }

}
