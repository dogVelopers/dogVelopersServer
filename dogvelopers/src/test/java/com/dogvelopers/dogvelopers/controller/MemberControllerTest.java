package com.dogvelopers.dogvelopers.controller;

import com.dogvelopers.dogvelopers.dto.member.MemberRequestDto;
import com.dogvelopers.dogvelopers.dto.member.MemberResponseDto;
import com.dogvelopers.dogvelopers.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
public class MemberControllerTest{
    MockMvc mockMvc;

    @Autowired
    MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("POST /members")
    void save() throws Exception {
        // given
        MemberRequestDto a = generateMemberRequestDto("네오");

        // when
        MockHttpServletRequestBuilder content = multipart("/members")
                                        .file(generatedFile())
                                        .flashAttr("memberRequestDto" , a);


        // then
        mockMvc.perform(content)
                        .andExpect(status().isCreated())
                        .andDo(print());
    }

    @Test
    @DisplayName("PUT /members/{memberId}")
    void update() throws Exception {
        // given
        MemberRequestDto saveDto = generateMemberRequestDto("네오");

        // when
        MemberResponseDto memberResponseDto = memberService.save(generatedFile() , saveDto);
        MemberRequestDto updateDto = generateMemberRequestDto("감나무");

        RequestPostProcessor postProcessor = new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        };

        MockHttpServletRequestBuilder content = multipart("/members/{memberId}" , memberResponseDto.getId())
                                        .file(generatedFile())
                                        .flashAttr("memberRequestDto" , updateDto)
                                        .with(postProcessor);

        // then
        mockMvc.perform(content)
                        .andExpect(status().isOk())
                        .andDo(print());
    }

    @Test
    @DisplayName("DELETE /members/{memberId}")
    void delete() throws Exception {
        // given
        MemberRequestDto deleteDto = generateMemberRequestDto("네오");

        // when
        MemberResponseDto memberResponseDto = memberService.save(generatedFile() , deleteDto);

        // then
        MockHttpServletRequestBuilder deleteContent = MockMvcRequestBuilders.delete("/members/{memberId}", memberResponseDto.getId())
                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(deleteContent)
                .andExpect(status().isNoContent())
                .andDo(print());

    }

    static private MemberRequestDto generateMemberRequestDto(String s){

        // 들어오는 문자열로 만들어서 반환 , image 는 그냥 null로 유지
        return MemberRequestDto.builder()
                .name(s)
                .major("소프")
                .studentId("201733009")
                .generation(15L)
                .birthDay(LocalDate.of(1998 , 6 , 5))
                .build();
    }

    static private String generatedFilePath(){
        return "/Users/jaeyeonkim/Downloads/KakaoTalk_Photo_2022-01-29-22-04-01.jpeg"; // 내가 가지고 있는 kakao talk 사진을 이용해서 multipartfile 생성
    }

    static private MockMultipartFile generatedFile() throws IOException {
        Resource fileResource = new ClassPathResource(generatedFilePath());

        return new MockMultipartFile("file",
                             "KakaoTalk_Photo_2022-01-29-22-04-01.jpeg",
                                "image/jpeg",
                                            new FileInputStream(generatedFilePath()));
    }

}
