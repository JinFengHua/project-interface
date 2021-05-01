package com.jdk.projectinterface.controller;

import com.jdk.projectinterface.bean.Attend;
import com.jdk.projectinterface.service.AttendService;
import com.jdk.projectinterface.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Map;


/**
 考勤任务表
 */
@RestController
@RequestMapping("/attend")
public class AttendController {
    @Autowired
    AttendService attendService;

    @GetMapping("/addAttend")
    public Object addAttend(
            @RequestParam("courseId") Integer courseId,
            @RequestParam("start") Timestamp start,
            @RequestParam("end") Timestamp end,
            @RequestParam("longitude") Double longitude,
            @RequestParam("latitude") Double latitude,
            @RequestParam("location") String location,
            @RequestParam("type") Integer type,
            @RequestParam(value = "gesture",required = false) String gesture
    ){
        Attend attend = new Attend(courseId,start,end,longitude,latitude,location,type);
        if (type == 2){
            attend.setAttendGesture(gesture);
        }
        return attendService.addAttend(attend);
    }

    /**
     * 修改
     */
    @GetMapping("/modifyAttend")
    public Object modifyAttend(
            @RequestParam("attendId") Integer attendId,
            @RequestParam(value = "courseId",required = false) Integer courseId,
            @RequestParam(value = "start",required = false) Timestamp start,
            @RequestParam(value = "end",required = false) Timestamp end,
            @RequestParam(value = "longitude",required = false) Double longitude,
            @RequestParam(value = "latitude",required = false) Double latitude,
            @RequestParam(value = "location",required = false) String location
     ){
        Attend attend = new Attend();
        attend.setAttendId(attendId);
        attend.setCourseId(Utils.isEmpty(courseId) ? null : courseId);
        attend.setAttendStart(Utils.isEmpty(start) ? null : start);
        attend.setAttendEnd(Utils.isEmpty(end) ? null : end);
        attend.setAttendLongitude(Utils.isEmpty(longitude) ? null : longitude);
        attend.setAttendLatitude(Utils.isEmpty(latitude) ? null : latitude);
        attend.setAttendLocation(Utils.isEmpty(location) ? null : location);
        return attendService.modifyAttend(attend);
    }

    /**
     * 查询
     */
    @GetMapping("/findAttendByColumn")
    public Object findAttendByColumn(
            @RequestParam("column") String column,
            @RequestParam("value") Object value
    ){
        return attendService.findAttendByColumn(column,value);
    }

    @GetMapping("/findAttendByMap")
    public Object findAttendByMap(@RequestParam Map<String, Object> map){
        return attendService.findAttendByMap(map);
    }

    @GetMapping("/findAttendByTime")
    public Object findAttendByTime(@RequestParam("courseId") Integer courseId,@RequestParam("time") String time){
        return attendService.findAttendByTime(courseId,time);
    }

    @GetMapping("/findAttendByCourseId")
    public Object findAttendByCourseId(@RequestParam("courseId") Integer courseId){
        return attendService.findAttendByCourseId(courseId);
    }

    @GetMapping("/findStudentAttend")
    public Object findStudentAttend(@RequestParam("courseId") Integer courseId,@RequestParam("joinTime") Timestamp joinTime){
        return attendService.findStudentAttend(courseId,joinTime);
    }

    @GetMapping("/findStudentAttendByTime")
    public Object findStudentAttendByTime(@RequestParam("courseId") Integer courseId,@RequestParam("joinTime") Timestamp joinTime,
                                          @RequestParam("time") String time){
        return attendService.findStudentAttendByTime(courseId,joinTime,time);
    }

    /**
     * 删除
     */
    @GetMapping("/deleteAttend")
    public Object deleteAttend(@RequestParam("attendId") Integer attendId){
        return attendService.deleteAttend(attendId);
    }

}
