package com.dogvelopers.dogvelopers.repository;

import com.dogvelopers.dogvelopers.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByJoinDateBetweenOrderByJoinDateDesc(LocalDateTime joinDate1 , LocalDateTime joinDate2);
    List<Member> findAllByOrderByJoinDateDesc();
}
