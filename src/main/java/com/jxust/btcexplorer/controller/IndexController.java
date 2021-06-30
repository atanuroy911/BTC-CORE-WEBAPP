package com.jxust.btcexplorer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    
    @RequestMapping("/")
    public String root() {
        System.out.println("index page");
        return "index.html";
    }
    @RequestMapping("/index")
    public String index() {
        System.out.println("index page");
        return "index.html";
    }
}
