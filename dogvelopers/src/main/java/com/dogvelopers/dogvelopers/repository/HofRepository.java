package com.dogvelopers.dogvelopers.repository;

import com.dogvelopers.dogvelopers.entity.Hof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HofRepository extends JpaRepository<Hof, Long>{

}
