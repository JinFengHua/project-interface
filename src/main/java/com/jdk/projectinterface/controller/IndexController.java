package com.jdk.projectinterface.controller;

import com.jdk.projectinterface.common.ServiceResponse;
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

    @PostMapping("/saveImage")
    @ResponseBody
    public Object test(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("dir") String dir
    ){
        return ServiceResponse.createResponse(Utils.saveImage(photo,dir));
    }
}
