package com.dogvelopers.dogvelopers.repository;

import com.dogvelopers.dogvelopers.dto.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project , Long> {
}
