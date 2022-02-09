package com.dogvelopers.dogvelopers.controller;

import com.dogvelopers.dogvelopers.service.MemberService;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
}
