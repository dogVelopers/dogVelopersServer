package com.dogvelopers.dogvelopers.controller;

import com.dogvelopers.dogvelopers.dto.hof.HofRequestDto;
import com.dogvelopers.dogvelopers.dto.hof.HofResponseDto;
import com.dogvelopers.dogvelopers.entity.Hof;
import com.dogvelopers.dogvelopers.repository.HofRepository;
import com.dogvelopers.dogvelopers.service.HofService;
import com.dogvelopers.dogvelopers.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

//@SpringBootTest
//@Transactional
@RunWith(SpringRunner.class)
@WebMvcTest(HofController.class)
class HofControllerTest {

    @Autowired
    MockMvc mvc;

//    @Autowired
//    HofController hofController;

    @Test
    @DisplayName("findAll test")
    void findAll() throws Exception {
        //given
//        ResponseEntity<HofResponseDto> hofs = hofController.findAll();
        //then
//        assertEquals(HttpStatus.OK , hofs.getStatusCode());

        mvc.perform(get("hofs"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("register test")
    void register() {
        //given


        //when


        //then


    }
}