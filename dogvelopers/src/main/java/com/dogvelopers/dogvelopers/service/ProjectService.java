package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }
}
