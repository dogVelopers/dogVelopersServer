package com.dogvelopers.dogvelopers.controller;

import com.dogvelopers.dogvelopers.service.ProjectService;
import org.springframework.stereotype.Controller;

@Controller
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }
}
