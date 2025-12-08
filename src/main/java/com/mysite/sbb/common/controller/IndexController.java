package com.mysite.sbb.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/abc/test")
    public String test(){
        return "temp/test";
    }

    @GetMapping("/temp/bban")
    public String bBan(){
        return "temp/bban";
    }
}
