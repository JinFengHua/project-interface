package com.jdk.projectinterface.controller;

import com.jdk.projectinterface.bean.Record;
import com.jdk.projectinterface.common.ServiceResponse;
import com.jdk.projectinterface.service.RecordService;
import com.jdk.projectinterface.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    RecordService recordService;

    /**
     * 进行签到操作，如果数据库没有该学生记录，则调用addRecord；若已经存在，则调用modifyRecord进行修改
     */
    @PostMapping("/doRecord")
    public Object addRecord(
            @RequestParam("attendId") Integer attendId,
            @RequestParam("studentId") Integer studentId,
            @RequestParam(value = "time") String time,
            @RequestParam(value = "longitude") String longitude,
            @RequestParam(value = "latitude") String latitude,
            @RequestParam(value = "photo") MultipartFile photo,
            HttpServletRequest request
    ){
        /**
         * 调用Utils方法，存储图片至数据库并返回图片地址的访问地址
         */
        String path = Utils.saveImage(photo, request, "checkimages");
        if (path.isEmpty()){
            return ServiceResponse.createFailResponse("图片上传失败，请重试");
        }
        /**
         * 从数据库查看当前学生的人脸信息位置，并与签到上传的图片进行比对识别，返回true或false
         * 先默认始终为真
         */
        Boolean recognitionResult = true;
        Boolean distanceResult = recordService.inClass(attendId,Double.valueOf(longitude),Double.valueOf(latitude));
        Record record = new Record(attendId,studentId,Timestamp.valueOf(time),Double.valueOf(longitude),Double.valueOf(latitude),path);
        if (recognitionResult && distanceResult){
            record.setRecordResult(3);
            record.setRecordRemark("签到成功");
        } else if (!recognitionResult && distanceResult){
            record.setRecordResult(2);
            record.setRecordRemark("人脸识别未通过,考勤未通过");
        } else if (recognitionResult && !distanceResult){
            record.setRecordResult(1);
            record.setRecordRemark("未在考勤指定范围,考勤未通过");
        } else {
            record.setRecordResult(0);
            record.setRecordRemark("考勤未通过");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("attend_id",attendId);
        map.put("student_id",studentId);
        Record recordQuery = recordService.findRecordByMap(map).getData().get(0);
        if (!Utils.isEmpty(recordQuery) ){
            Utils.deleteImage(recordQuery.getRecordPhoto(),"checkimages");
            return recordService.modifyRecord(record);
        }
        return recordService.addRecord(record);
    }

    @GetMapping("/modifyRecord")
    public Object modifyRecord(
            @RequestParam("attendId") Integer attendId,
            @RequestParam("studentId") Integer studentId,
            @RequestParam("result") Integer result
    ){
        Record record = new Record();
        record.setAttendId(attendId);
        record.setStudentId(studentId);
        record.setRecordResult(result);
        record.setRecordRemark("教师代签");
        return recordService.modifyRecord(record);
    }

    /**
     * 删除
     */
    @GetMapping("/deleteRecord")
    public Object deleteRecord(
            @RequestParam("attendId") Integer attendId,
            @RequestParam("studentId") Integer studentId
    ){
        return recordService.deleteRecord(attendId,studentId);
    }

    /**
     * 查找
     */
    @GetMapping("/findRecordByColumn")
    public Object findRecordByColumn(String column,Object value){
        return recordService.findRecordByColumn(column,value);
    }

    @GetMapping("/findRecordByMap")
    public Object findRecordByMap(@RequestParam Map<String, Object> map){
        return recordService.findRecordByMap(map);
    }

    @GetMapping("/findRecordByTime")
    public Object findRecordByTime(@RequestParam("time") String time){
        return recordService.findRecordByTime(time);
    }

}
