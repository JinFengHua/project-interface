package com.jdk.projectinterface.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jdk.projectinterface.bean.Attend;
import com.jdk.projectinterface.bean.Leave;
import com.jdk.projectinterface.bean.Record;
import com.jdk.projectinterface.common.ServiceResponse;
import com.jdk.projectinterface.mapper.AttendMapper;
import com.jdk.projectinterface.mapper.LeaveMapper;
import com.jdk.projectinterface.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LeaveService {
    @Autowired
    LeaveMapper leaveMapper;

    @Autowired
    RecordMapper recordMapper;

    @Autowired
    AttendMapper attendMapper;


    public ServiceResponse<Leave> addLeave(Leave leave) {
        leaveMapper.insert(leave);
        return ServiceResponse.createResponse("创建成功");
    }

    public ServiceResponse<List<Leave>> findLeaveByMap(Map<String, Object> map) {
        List<Leave> leaves = leaveMapper.selectByMap(map);
        return ServiceResponse.createResponse("查询成功",leaves);
    }

    public ServiceResponse<List<Leave>> findLeaveByColumn(String column,Object value) {
        List<Leave> leaves = leaveMapper.selectList(new QueryWrapper<Leave>().like(column,value));
        return ServiceResponse.createResponse("查询成功",leaves);
    }

    public ServiceResponse<Leave> modifyLeave(Leave leave) {
        if (leave.getApprovalResult() == 2){
            Record record = new Record();
            record.setStudentId(leave.getStudentId());
            /**
             * 首先通过课程id找到该课程下的所有考勤任务
             * 再选择考勤时间在请假时间内的部分，获得他们的attendID
             * 将这些任务中该生的状态设为请假
             */
            List<Attend> needLeaveAttend = attendMapper.findNeedLeaveAttend(leave.getCourseId(), leave.getLeaveTime(), leave.getBackTime());
            for (Attend attend : needLeaveAttend) {
                record.setAttendId(attend.getAttendId());
                record.setRecordResult(3);
                recordMapper.update(record,new UpdateWrapper<Record>().eq("attend_id",record.getAttendId()).eq("student_id",record.getStudentId()));
            }
        }
        leaveMapper.updateById(leave);
        return ServiceResponse.createResponse("审批成功");
    }

    public ServiceResponse<Leave> deleteLeave(Integer leaveId) {
        leaveMapper.deleteById(leaveId);
        return ServiceResponse.createResponse("删除成功");
    }

    public ServiceResponse<List<Leave>> findAllLeave(Integer courseId) {
        List<Leave> allLeave = leaveMapper.findAllLeave(courseId);
        return ServiceResponse.createResponse("查询成功",allLeave);

    }

    public ServiceResponse<List<Leave>> findAllLeaveByStudentId(Integer courseId, Integer studentId) {
        List<Leave> leaves = leaveMapper.selectList(new QueryWrapper<Leave>().eq("course_id",courseId).eq("student_id", studentId).orderByDesc("leave_time"));
        return ServiceResponse.createResponse("查询成功",leaves);
    }

    public ServiceResponse<List<Leave>> findAllLeaveWithStudent() {
        List<Leave> allLeave = leaveMapper.findAllLeaveWithStudent();
        return ServiceResponse.createResponse("查询成功",allLeave);

    }
}
