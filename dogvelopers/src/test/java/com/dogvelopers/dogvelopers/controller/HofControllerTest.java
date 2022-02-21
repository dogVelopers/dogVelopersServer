package com.dogvelopers.dogvelopers.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class HofControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void findAll() throws Exception {
        mvc.perform(get("/hofs"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void register() {
    }
}