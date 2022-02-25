package com.dogvelopers.dogvelopers.repository;

import com.dogvelopers.dogvelopers.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByGenerationOrderByGenerationDesc(Long generation);

    List<Member> findAllByOrderByGenerationDesc();
}
