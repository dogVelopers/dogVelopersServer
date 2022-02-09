package com.dogvelopers.dogvelopers.controller;

import com.dogvelopers.dogvelopers.service.HofService;
import org.springframework.stereotype.Controller;

@Controller
public class HofController {

    private HofService hofService;

    public HofController(HofService hofService){
        this.hofService = hofService;
    }
}
