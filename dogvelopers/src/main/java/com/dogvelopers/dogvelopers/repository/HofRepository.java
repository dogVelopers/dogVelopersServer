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
public interface HofRepository extends JpaRepository<Hof, Long>{

    boolean existsByMemberId(Long memberId);

    Optional<Hof> findByMemberId(Long memberId);

    Page<Hof> findAll(Pageable pageable);

    @Query("select h " +
           "from Hof h " +
           "order by h.member.generation DESC")
    Page<Hof> findAllByOrderByGenerationDesc(Pageable pageable); // query 로 정렬되어서 들어오게 (jpql)

    @Query("select h " +
            "from Hof h " +
            "order by h.member.generation DESC")
    List<Hof> findAllByOrderByGenerationDesc(); // pagination 적용 안된 버전

    @Query("select h " +
            "from Hof h " +
            "where h.member.generation = :generation " +
            "order by h.member.generation DESC")
    List<Hof> findByGenerationOrderByGenerationDesc(Long generation); // generation 으로 찾을 수 있게 (join 을 이용해서)
}
