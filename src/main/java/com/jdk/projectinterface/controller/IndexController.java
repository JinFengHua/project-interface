package com.jdk.projectinterface.controller;

import com.jdk.projectinterface.bean.Admini;
import com.jdk.projectinterface.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @GetMapping({"/","index"})
    public String showIndex(){
        return "index";
    }

    @PostMapping("/test")
    @ResponseBody
    public String test(
            @RequestParam("photo") MultipartFile photo,
            HttpServletRequest request
    ){
        return Utils.saveImage(photo,request,"test");
    }
}
