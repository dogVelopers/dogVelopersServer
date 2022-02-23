package com.dogvelopers.dogvelopers.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class adminController {

    @GetMapping("/hofs")
    public String setHof(){
        return "hofs/createHofForm";
    }

    @GetMapping("/members")
    public String setMember(){
        return "members/createMemberForm";
    }

    @GetMapping("projects")
    public String setProject(){
        return "projects/createProjectForm";
    }
}
