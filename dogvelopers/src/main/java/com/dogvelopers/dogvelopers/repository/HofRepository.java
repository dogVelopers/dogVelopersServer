package com.dogvelopers.dogvelopers.repository;

import com.dogvelopers.dogvelopers.entity.Hof;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HofRepository extends JpaRepository<Hof, Long> {

    boolean existsByMemberId(Long memberId);

    Optional<Hof> findByMemberId(Long memberId);

    Page<Hof> findAll(Pageable pageable);

    @Query("select h " +
            "from Hof h " +
            "order by h.member.generation ASC")
    List<Hof> findAllByOrderByGenerationAsc(); // pagination 적용 안된 버전

    @Query("select h " +
            "from Hof h " +
            "order by h.member.generation DESC")
    List<Hof> findAllByOrderByGenerationDesc(); // pagination 적용 안된 버전

    @Query("select h " +
            "from Hof h " +
            "order by h.member.studentId ASC")
    List<Hof> findAllByOrderByStudentIdAsc(); // pagination 적용 안된 버전

    @Query("select h " +
            "from Hof h " +
            "order by h.member.studentId DESC")
    List<Hof> findAllByOrderByStudentIdDesc(); // pagination 적용 안된 버전

    @Query("select h " +
            "from Hof h " +
            "where h.member.generation = :generation " +
            "order by h.member.generation DESC")
    List<Hof> findByGenerationOrderByGenerationDesc(Long generation); // generation 으로 찾을 수 있게 (join 을 이용해서)

    @Query("select h " +
            "from Hof h " +
            "order by h.member.generation ASC")
    Page<Hof> findAllByOrderByGenerationAsc(Pageable pageable); // Generation ASC

    @Query("select h " +
            "from Hof h " +
            "order by h.member.generation DESC")
    Page<Hof> findAllByOrderByGenerationDesc(Pageable pageable); // Generation Desc

    @Query("select h " +
            "from Hof h " +
            "order by h.member.studentId ASC")
    Page<Hof> findAllByOrderByStudentIdAsc(Pageable pageable); // StudentId Desc

    @Query("select h " +
            "from Hof h " +
            "order by h.member.studentId DESC")
    Page<Hof> findAllByOrderByStudentIdDesc(Pageable pageable); // StudentId Desc


}
