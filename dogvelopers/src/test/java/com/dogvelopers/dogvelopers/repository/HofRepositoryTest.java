package com.dogvelopers.dogvelopers.repository;

import com.dogvelopers.dogvelopers.entity.Hof;
import com.dogvelopers.dogvelopers.entity.Member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Transactional
public class HofRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired HofRepository hofRepository;

    @Test
    @DisplayName("Hof 목록 generation 의 역순으로 정렬해서 pagination 적용")
    void findAllByOrderByGenerationDesc() {
        // given
        Member m1 = generateMember(1L);
        Member m2 = generateMember(2L);
        Member m3 = generateMember(3L);
        Member m4 = generateMember(4L);

        m1 = memberRepository.save(m1);
        m2 = memberRepository.save(m2);
        m3 = memberRepository.save(m3);
        m4 = memberRepository.save(m4); // m들 id까지 있도록 갱신

        hofRepository.save(generateHof(m1));
        hofRepository.save(generateHof(m2));
        hofRepository.save(generateHof(m3));
        hofRepository.save(generateHof(m4));

        // when
        PageRequest pageRequest = PageRequest.of(0, 3);
        List<Hof> hofs = hofRepository.findAllByOrderByGenerationDesc(pageRequest).getContent(); // getContent 하면 Page -> List 로 변경 가능

        // then
        int index = 0;

        for(int i = 4; i != 1; i--){ // 4 ~ 1 번 순으로 조회 되었는지 , 확인
            assertEquals(hofs.get(index++).getMember().getGeneration() , new Long(i));
        }
        assertEquals(hofs.size(), 3);
    }

    Member generateMember(Long generation) {
        return Member.builder()
                .name("김재연")
                .major("소프")
                .birthDay(LocalDate.of(1998, 6, 5))
                .generation(generation)
                .studentId("201733009")
                .build();
    }

    Hof generateHof(Member member){
        return Hof.builder()
                .member(member)
                .introduction("안녕하세요")
                .company("카카오")
                .build();
    }
}
