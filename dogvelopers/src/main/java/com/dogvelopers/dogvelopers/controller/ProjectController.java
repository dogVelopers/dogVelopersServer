package com.dogvelopers.dogvelopers.controller;

import com.dogvelopers.dogvelopers.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
}
