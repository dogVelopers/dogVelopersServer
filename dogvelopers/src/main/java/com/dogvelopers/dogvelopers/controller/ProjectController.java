package com.dogvelopers.dogvelopers.controller;

import com.dogvelopers.dogvelopers.dto.ProjectRequestDto;
import com.dogvelopers.dogvelopers.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<ProjectRequestDto> findAll() {
        return new ResponseEntity(projectService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{postId}")
    public ResponseEntity<ProjectRequestDto> findById(@PathVariable("postId") Long id) {
        return new ResponseEntity(projectService.findById(id), HttpStatus.OK);
    }

}
