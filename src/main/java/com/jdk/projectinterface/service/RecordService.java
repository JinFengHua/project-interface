package com.jdk.projectinterface.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdk.projectinterface.bean.*;
import com.jdk.projectinterface.common.ServiceResponse;
import com.jdk.projectinterface.mapper.CourseStudentMapper;
import com.jdk.projectinterface.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecordService {
    @Autowired
    RecordMapper recordMapper;

    @Autowired
    AttendService attendService;

    @Autowired
    CourseStudentService courseStudentService;

    public ServiceResponse<Record> modifyRecord(Record record){
        /**
         * 在修改之前，先检查其
         */
        recordMapper.update(record, new QueryWrapper<Record>().eq("attend_id", record.getAttendId()).eq("student_id", record.getStudentId()));
        String message = record.getRecordResult() == 1 ? "人脸识别未通过" : "签到成功";
        return ServiceResponse.createResponse(message);
    }

    /**
     * 查找
     */
    public ServiceResponse<List<Record>> findRecordByMap(Map<String,Object> map) {
        List<Record> records = recordMapper.selectByMap(map);
        return ServiceResponse.createResponse("查找成功",records);
    }

//    通过考勤id查询到当前所有该考勤下的数据
    public ServiceResponse<List<Record>> findAllRecord(Integer attendId){
        List<Record> records = recordMapper.findAllRecord(attendId);
        return ServiceResponse.createResponse("查找成功",records);
    }

    public ServiceResponse<List<Record>> findRecordByColumn(String column,Object value){
        List<Record> records = recordMapper.selectList(new QueryWrapper<Record>().like(column, value));
        return ServiceResponse.createResponse("查找成功",records);
    }

    /**
     * 通过时间戳类型模糊查询
     */
    public ServiceResponse<List<Record>> findRecordByTime(String value){
        List<Record> records = recordMapper.findRecordByTime(value);
        return ServiceResponse.createResponse("查询成功",records);
    }

    /**
     * 删除
     */
    public ServiceResponse<Record> deleteRecord(Integer attendId,Integer studentId){
        recordMapper.delete(new QueryWrapper<Record>().eq("attend_id", attendId).eq("student_id", studentId));
        return ServiceResponse.createResponse("删除成功");
    }

    /**
     * 统计所有学生的考勤记录
     */
    public ServiceResponse<List<Statistics>> findAllStudentRecord(Integer courseId) {
        List<CourseStudent> allStudent = courseStudentService.findAllByCourseId(courseId).getData();
        List<Attend> attends = attendService.findAttendByCourseId(courseId).getData();
        QueryWrapper<Record> wrapper = new QueryWrapper<>();
        List<Statistics> list = new ArrayList<>();
        for (CourseStudent courseStudent : allStudent) {
            Student student = courseStudent.getStudent();
            Integer absentNum = 0;
            Integer failNum = 0;
            Integer successNum = 0;
            Integer leaveNum = 0;
            for (Attend attend : attends) {
                absentNum = absentNum + recordMapper.selectCount(wrapper.eq("attend_id",attend.getAttendId()).eq("student_id", student.getStudentId()).eq("record_result", 0));
                wrapper.clear();
                failNum = failNum + recordMapper.selectCount(wrapper.eq("attend_id",attend.getAttendId()).eq("student_id", student.getStudentId()).eq("record_result", 1));
                wrapper.clear();
                successNum = successNum + recordMapper.selectCount(wrapper.eq("attend_id",attend.getAttendId()).eq("student_id", student.getStudentId()).eq("record_result", 2));
                wrapper.clear();
                leaveNum = leaveNum + recordMapper.selectCount(wrapper.eq("attend_id",attend.getAttendId()).eq("student_id", student.getStudentId()).eq("record_result", 3));
                wrapper.clear();
            }
            list.add(new Statistics(student.getStudentName(),student.getStudentAccount(),absentNum,failNum,successNum,leaveNum));
        }
        return ServiceResponse.createResponse("查询成功",list);
    }

    public ServiceResponse<List<Record>> findAllRecordWithAttend(){
        List<Record> records = recordMapper.findAllRecordWithAttend();
        return ServiceResponse.createResponse("查找成功",records);
    }
}
