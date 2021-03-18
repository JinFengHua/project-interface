package com.jdk.projectinterface.controller;

import com.jdk.projectinterface.bean.Total;
import com.jdk.projectinterface.service.TotalService;
import com.jdk.projectinterface.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/total")
public class TotalController {
    @Autowired
    TotalService totalService;

    /**
     *添加
     */
    @GetMapping("/addTotal")
    public Object addTotal(
            @RequestParam("courseCode") String courseCode,
            @RequestParam("studentId") Integer studentId
    ){
        return totalService.addTotal(courseCode,studentId);
    }

    /**
     *查询
     */
    @GetMapping("/findTotalByMap")
    public Object findTotal(@RequestParam Map<String, Object> map){
        return totalService.findTotalByMap(map);
    }

    @GetMapping("/findTotalByColumn")
    public Object findTotalByColumn(
            @RequestParam("column") String column,
            @RequestParam("value") Object value
    ){
        return totalService.findTotalByColumn(column,value);
    }

    @GetMapping("/findAll")
    public Object findAll(){
        return totalService.findAll();
    }

    /**
     *修改
     */
    @GetMapping("/modifyTotal")
    public Object modifyTotal(
            @RequestParam("courseId") Integer courseId,
            @RequestParam("studentId") Integer studentId,
            @RequestParam(value = "numberSuccess",required = false) Integer numberSuccess,
            @RequestParam(value = "numberFail", required = false) Integer numberFail,
            @RequestParam(value = "numberAbsent", required = false) Integer numberAbsent,
            @RequestParam(value = "number", required = false) Integer numberLeave
    ){
        Total total = new Total(courseId,studentId);
        total.setNumberSuccess(Utils.isEmpty(numberSuccess) ? null : numberSuccess);
        total.setNumberFail(Utils.isEmpty(numberFail) ? null : numberFail);
        total.setNumberAbsent(Utils.isEmpty(numberAbsent) ? null : numberAbsent);
        total.setNumberLeave(Utils.isEmpty(numberLeave) ? null : numberLeave);
        return totalService.modifyTotal(total);
    }

    /**
     *删除
     */
    @GetMapping("/deleteTotal")
    public Object deleteTotal(
            @RequestParam("courseId") Integer courseId,
            @RequestParam("studentId") Integer studentId
    ){
        return totalService.deleteTotal(courseId,studentId);
    }
}
