package com.dogvelopers.dogvelopers.controller;

import com.dogvelopers.dogvelopers.dto.hof.HofRequestDto;
import com.dogvelopers.dogvelopers.dto.hof.HofResponseDto;
import com.dogvelopers.dogvelopers.dto.member.MemberRequestDto;
import com.dogvelopers.dogvelopers.dto.member.MemberResponseDto;
import com.dogvelopers.dogvelopers.repository.MemberRepository;
import com.dogvelopers.dogvelopers.service.HofService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.transaction.Transactional;
import java.time.LocalDate;

@SpringBootTest
@Transactional
class HofControllerTest {

    MockMvc mockMvc;

    @Autowired
    HofService hofService;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("POST /hofs")
    void save() throws Exception {
        // given
        MemberResponseDto memberResponseDto = saveDto(generateMemberRequestDto());
        HofRequestDto a = generateHofRequestDto(memberResponseDto.getId() ,  "배달의 민족");

        // when
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/hofs")
                .contentType(MediaType.APPLICATION_JSON)
                .flashAttr("hofRequestDto" , a);

        // then
        mockMvc.perform(content)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("PUT /hofs/{hofId}")
    void update() throws Exception {
        // given
        MemberResponseDto memberResponseDto = saveDto(generateMemberRequestDto());
        HofRequestDto saveDto = generateHofRequestDto(memberResponseDto.getId() , "배달의 민족"); // 처음에는 배달의 민족으로 저장

        // when
        HofResponseDto hofResponseDto = hofService.save(saveDto); // id를 얻어서 업데이트를 하기 위한 save
        HofRequestDto updateDto = generateHofRequestDto(memberResponseDto.getId() , "카카오");   // 그 다음에 다시 카카오로 바꾼다.

        // then
        MockHttpServletRequestBuilder updateContent = MockMvcRequestBuilders.put("/hofs/{hofId}", hofResponseDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .flashAttr("hofRequestDto" , updateDto);

        mockMvc.perform(updateContent)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("DELETE /hofs/{hofId}")
    void delete() throws Exception {
        // given
        MemberResponseDto memberResponseDto = saveDto(generateMemberRequestDto());
        HofRequestDto deleteDto = generateHofRequestDto(memberResponseDto.getId() , "배달의 민족");

        // when
        HofResponseDto hofResponseDto = hofService.save(deleteDto);

        // then
        MockHttpServletRequestBuilder deleteContent = MockMvcRequestBuilders.delete("/hofs/{hofId}", hofResponseDto.getId())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(deleteContent)
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

    }

    static private HofRequestDto generateHofRequestDto(Long memberId , String s) {
        return HofRequestDto.builder()
                .memberId(memberId)
                .company(s)
                .introduction("안녕하세요")
                .build();
    }

    public MemberResponseDto saveDto(MemberRequestDto memberRequestDto){
        return new MemberResponseDto(memberRepository.save(memberRequestDto.toEntity()));
        // dto를 저장하면서 , ResponseDto 를 반환
    }

    static private MemberRequestDto generateMemberRequestDto(){

        // 들어오는 문자열로 만들어서 반환 , image 는 그냥 null로 유지
        return MemberRequestDto.builder()
                .name("김재연")
                .major("소프")
                .studentId("201733009")
                .generation(15L)
                .birthDay(LocalDate.of(1998 , 6 , 5))
                .build();
    }
}