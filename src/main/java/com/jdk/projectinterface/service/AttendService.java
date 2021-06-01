package com.jdk.projectinterface.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jdk.projectinterface.bean.Attend;
import com.jdk.projectinterface.bean.CourseStudent;
import com.jdk.projectinterface.bean.Leave;
import com.jdk.projectinterface.bean.Record;
import com.jdk.projectinterface.common.ServiceResponse;
import com.jdk.projectinterface.mapper.AttendMapper;
import com.jdk.projectinterface.mapper.CourseStudentMapper;
import com.jdk.projectinterface.mapper.LeaveMapper;
import com.jdk.projectinterface.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class AttendService {
    @Autowired
    AttendMapper attendMapper;

    @Autowired
    LeaveMapper leaveMapper;

    @Autowired
    RecordMapper recordMapper;

    @Autowired
    CourseStudentMapper courseStudentMapper;

    /**
     * 添加
     */
    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse<Attend> addAttend(Attend attend) {
        try {
            attendMapper.insert(attend);

            List<CourseStudent> courseStudents = courseStudentMapper.selectList(new QueryWrapper<CourseStudent>().eq("course_id", attend.getCourseId()));
            Record record = new Record();
            record.setAttendId(attend.getAttendId());
//        将所有参与考勤学生结果初始化为0
            for (CourseStudent courseStudent : courseStudents) {
                record.setStudentId(courseStudent.getStudentId());
                record.setRecordResult(0);
                recordMapper.insert(record);
            }
//        再去查看是否有请假学生，在申请请假表时也会查看考勤表
            List<Leave> leavedStudent = leaveMapper.findLeavedStudent(attend.getCourseId(), attend.getAttendStart(), attend.getAttendEnd());
            for (Leave leave : leavedStudent) {
                record.setStudentId(leave.getStudentId());
                record.setRecordResult(3);
                recordMapper.update(record,new UpdateWrapper<Record>().eq("student_id",record.getStudentId()).eq("attend_id",record.getAttendId()));
            }

            return ServiceResponse.createResponse("创建成功");
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return ServiceResponse.createFailResponse("创建错误，请重试");
        }
    }

    /**
     * 修改
     */
    public ServiceResponse<Attend> modifyAttend(Attend attend){
        attendMapper.updateById(attend);
        return ServiceResponse.createResponse("修改成功");
    }

    /**
     * 查询
     */
    public ServiceResponse<List<Attend>> findAttendByColumn(String column,Object value){
        List<Attend> attends = attendMapper.selectList(new QueryWrapper<Attend>().like(column, value).orderByDesc("attend_start"));
        return ServiceResponse.backFailResponse("查询结果为空","查询成功", attends);
    }

    public ServiceResponse<List<Attend>> findAttendByMap(Map<String, Object> map){
        List<Attend> attends = attendMapper.selectByMap(map);
        return ServiceResponse.backFailResponse("查询结果为空","查询成功", attends);
    }

    public ServiceResponse<List<Attend>> findAttendByCourseId(Integer courseId){
        List<Attend> attends = attendMapper.selectList(new QueryWrapper<Attend>().eq("course_id", courseId).orderByDesc("attend_start"));
        return ServiceResponse.createResponse("查询成功",attends);
    }

    /**
     * 删除
     */
    public ServiceResponse<Attend> deleteAttend(Integer attendId){
        attendMapper.deleteById(attendId);
        return ServiceResponse.createResponse("删除成功");
    }

    public ServiceResponse<List<Attend>> findStudentAttend(Integer courseId, Timestamp joinTime) {
        List<Attend> attends = attendMapper.findStudentAttend(courseId,joinTime);
        return ServiceResponse.createResponse("查询成功",attends);
    }

    public ServiceResponse<List<Attend>> findStudentAttendByTime(Integer courseId, Timestamp joinTime, String time) {
        List<Attend> attends = attendMapper.findStudentAttendByTime(courseId,joinTime,time);
        return ServiceResponse.createResponse("查询成功",attends);
    }

    public ServiceResponse<List<Attend>> findAttendByTime(Integer courseId, String time) {
        List<Attend> attends = attendMapper.findAttendByTime(courseId,time);
        return ServiceResponse.createResponse("查询成功",attends);
    }
}
