package com.jdk.projectinterface.controller;

import com.jdk.projectinterface.bean.Course;
import com.jdk.projectinterface.bean.Leave;
import com.jdk.projectinterface.common.ServiceResponse;
import com.jdk.projectinterface.service.CourseService;
import com.jdk.projectinterface.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/leave")
public class LeaveController {
    @Autowired
    LeaveService leaveService;

    @Autowired
    CourseService courseService;

    /**
     * 学生创建请假申请
     * result= 0正在审批,1拒绝请假,2批准请假
     */
    @GetMapping("/addLeave")
    public Object addLeave(
            @RequestParam("studentId") Integer studentId,
            @RequestParam("courseId") Integer courseId,
            @RequestParam("leaveTime") String leaveTime,
            @RequestParam("backTime") String backTime,
            @RequestParam("leaveReason") String leaveReason
    ){
        Leave leave = new Leave(studentId,courseId, Timestamp.valueOf(leaveTime),Timestamp.valueOf(backTime),leaveReason);
        leave.setApprovalResult(0);
        return leaveService.addLeave(leave);
    }

    /**
     * 查看课程中的请假申请
     */
    @GetMapping("/findLeaveByMap")
    public Object findLeaveByMap(@RequestParam Map<String, Object> map){
        return leaveService.findLeaveByMap(map);
    }

    /**
     * 查询所有该课程的请假申请
     */
    @GetMapping("/findAllLeave")
    public Object findAllLeave(@RequestParam("courseId") Integer courseId){
        return leaveService.findAllLeave(courseId);
    }

    @GetMapping("/findAllLeaveWithStudent")
    public Object findAllLeaveWithStudent(){
        return leaveService.findAllLeaveWithStudent();
    }

    @GetMapping("/findAllLeaveByStudentId")
    public Object findAllLeaveByStudentId(@RequestParam("courseId") Integer courseId,
                                          @RequestParam("studentId") Integer studentId){
        return leaveService.findAllLeaveByStudentId(courseId,studentId);
    }

    @GetMapping("/findLeaveByColumn")
    public Object findLeaveByColumn(
            @RequestParam("column") String column,
            @RequestParam("value") String value
    ){
        return leaveService.findLeaveByColumn(column,value);
    }

    /**
     * 教师审批课程中的请假申请
     */
    @GetMapping("/modifyLeave")
    public Object modifyLeave(
            @RequestParam("leaveId") Integer leaveId,
            @RequestParam("time") String time,
            @RequestParam("result") Integer result,
            @RequestParam("remark") String remark
    ){
        Map<String, Object> map = new HashMap<>();
        map.put("leave_id",leaveId);
        Leave leave = leaveService.findLeaveByMap(map).getData().get(0);
        leave.setApprovalRemark(remark);
        leave.setApprovalResult(result);
        leave.setApprovalTime(Timestamp.valueOf(time));
        return leaveService.modifyLeave(leave);
    }

    /**
     * 学生撤销申请
     */
    @GetMapping("/deleteLeave")
    public Object deleteLeave(@RequestParam("leaveId") Integer leaveId){
        Map<String, Object> map = new HashMap<>();
        map.put("leave_id",leaveId);
        Integer approvalResult = leaveService.findLeaveByMap(map).getData().get(0).getApprovalResult();
        if (approvalResult != 0){
            return ServiceResponse.createFailResponse("已审批，无法撤销");
        }
        return leaveService.deleteLeave(leaveId);
    }
}
