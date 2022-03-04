package com.dogvelopers.dogvelopers.repository;

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
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("Member 목록 generation 의 역순으로 정렬해서 pagination 적용")
    void findAllByOrderByGenerationDesc() {
        // given
        Member m1 = generateMember(1L);
        Member m2 = generateMember(2L);
        Member m3 = generateMember(3L);
        Member m4 = generateMember(4L);

        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);
        memberRepository.save(m4);

        // when
        PageRequest pageRequest = PageRequest.of(0, 3);
        List<Member> members = memberRepository.findAllByOrderByGenerationDesc(pageRequest).getContent(); // getContent 하면 Page -> List 로 변경 가능

        // then
        int index = 0;

        for(int i = 4; i != 1; i--){ // 4 ~ 1 번 순으로 조회 되었는지 , 확인
            assertEquals(members.get(index++).getGeneration() , new Long(i));
        }
        assertEquals(members.size(), 3);
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
}
