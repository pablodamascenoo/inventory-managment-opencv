package com.untree.inventory_managment_opencv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
