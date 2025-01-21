package com.untree.inventory_managment_opencv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CameraController {

    @GetMapping("/camera")
    public String getMethodName() {
        return "camera";
    }

}
