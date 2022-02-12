package com.dogvelopers.dogvelopers.repository;

import com.dogvelopers.dogvelopers.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project , Long> {

}
