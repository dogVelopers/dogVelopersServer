package com.dogvelopers.dogvelopers.service;

import com.dogvelopers.dogvelopers.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

}
