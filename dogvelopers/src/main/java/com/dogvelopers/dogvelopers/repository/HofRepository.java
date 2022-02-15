package com.dogvelopers.dogvelopers.repository;

import com.dogvelopers.dogvelopers.entity.Hof;
import com.dogvelopers.dogvelopers.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HofRepository extends JpaRepository<Hof, Long>{
    boolean existsByMemberId(Long memberId);
}
