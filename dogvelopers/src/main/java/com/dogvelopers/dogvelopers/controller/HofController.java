package com.dogvelopers.dogvelopers.controller;

import com.dogvelopers.dogvelopers.service.HofService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class HofController {

    private final HofService hofService;
}
