package com.jdk.projectinterface.controller;

import com.jdk.projectinterface.common.ServiceResponse;
import com.jdk.projectinterface.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/document")
public class ImageController {

    @PostMapping("/saveImage")
    @ResponseBody
    public Object test(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("type") String type,
            @RequestParam("id") String id
    ){
        String s = Utils.saveImage(photo, type, id);
        if (s != null){
            return ServiceResponse.createResponse(s);
        }
        return ServiceResponse.createResponse("false");
    }
}
