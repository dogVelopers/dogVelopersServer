package com.dogvelopers.dogvelopers.repository;

import com.dogvelopers.dogvelopers.entity.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project , Long> {

    @Query("select p " +
            "from Project p " +
            "order by p.startDate")
    List<Project> findProjectsOrderByStartDate(Pageable pageable);

}
