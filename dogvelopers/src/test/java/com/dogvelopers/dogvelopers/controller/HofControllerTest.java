package com.dogvelopers.dogvelopers.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;


//@SpringBootTest
//@Transactional
@WebMvcTest(HofController.class)
class HofControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("findAll test")
    void findAll() throws Exception {
    }

    @Test
    @DisplayName("register test")
    void register() {

    }
}