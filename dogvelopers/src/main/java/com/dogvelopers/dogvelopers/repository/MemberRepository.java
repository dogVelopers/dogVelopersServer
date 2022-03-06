package com.dogvelopers.dogvelopers.repository;

import com.dogvelopers.dogvelopers.entity.Hof;
import com.dogvelopers.dogvelopers.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByGenerationOrderByGenerationDesc(Long generation);

    Page<Member> findAll(Pageable pageable); // findAll pagination 적용

    Page<Member> findAllByOrderByGenerationAsc(Pageable pageable); // Generation ASC

    Page<Member> findAllByOrderByGenerationDesc(Pageable pageable); // Generation Desc

    Page<Member> findAllByOrderByStudentIdAsc(Pageable pageable); // StudentId Desc

    Page<Member> findAllByOrderByStudentIdDesc(Pageable pageable); // StudentId Desc

    List<Member> findAllByOrderByGenerationAsc(); // pagination 적용 안된 버전

    List<Member> findAllByOrderByGenerationDesc(); // pagination 적용 안된 버전

    List<Member> findAllByOrderByStudentIdAsc(); // pagination 적용 안된 버전

    List<Member> findAllByOrderByStudentIdDesc(); // pagination 적용 안된 버전

}
