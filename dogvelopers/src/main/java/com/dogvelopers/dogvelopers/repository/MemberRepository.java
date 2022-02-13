package com.dogvelopers.dogvelopers.repository;

import com.dogvelopers.dogvelopers.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
