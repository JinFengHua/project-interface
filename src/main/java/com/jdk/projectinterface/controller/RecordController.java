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
    public Object doRecord(
            @RequestParam("attendId") Integer attendId,
            @RequestParam("studentId") Integer studentId,
            @RequestParam(value = "time") String time,
            @RequestParam(value = "location") String location,
            @RequestParam(value = "photo") MultipartFile photo
    ){
        /**
         * 调用Utils方法，存储图片至数据库并返回图片地址的访问地址
         */
        String path = Utils.saveImage(photo, "checkimages");
        if (path.isEmpty()){
            return ServiceResponse.createFailResponse("图片上传失败，请重试");
        }
        /**
         * 从数据库查看当前学生的人脸信息位置，并与签到上传的图片进行比对识别，返回true或false
         * 先默认始终为真
         */
        Boolean recognitionResult = true;
        Record record = new Record(attendId,studentId,Timestamp.valueOf(time),location,path);
        if (recognitionResult){
            record.setRecordResult(2);
        } else {
            record.setRecordResult(1);
        }

//        删除原有图片
        Map<String, Object> map = new HashMap<>();
        map.put("attend_id",attendId);
        map.put("student_id",studentId);
        List<Record> data = recordService.findRecordByMap(map).getData();
        if (data.get(0).getRecordPhoto() != null) {
            Utils.deleteImage(data.get(0).getRecordPhoto(), "checkimages");
        }

        return recordService.modifyRecord(record);
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

    @GetMapping("/findAllRecord")
    public Object findRecordByColumn(@RequestParam("attendId") Integer attendId){
        return recordService.findAllRecord(attendId);
    }

    @GetMapping("/findRecordByMap")
    public Object findRecordByMap(@RequestParam Map<String, Object> map){
        return recordService.findRecordByMap(map);
    }

    @GetMapping("/findRecordByTime")
    public Object findRecordByTime(@RequestParam("time") String time){
        return recordService.findRecordByTime(time);
    }

    @GetMapping("/findAllStudentRecord")
    public Object findAllStudentRecord(@RequestParam("courseId") Integer courseId){
        return recordService.findAllStudentRecord(courseId);
    }

}
