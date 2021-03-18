package com.jdk.projectinterface.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdk.projectinterface.bean.Course;
import com.jdk.projectinterface.bean.Total;
import com.jdk.projectinterface.common.ServiceResponse;
import com.jdk.projectinterface.mapper.TotalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TotalService {
    @Autowired
    TotalMapper totalMapper;

    @Autowired
    CourseService courseService;

    /**
     *添加
     */
    public ServiceResponse<Total> addTotal(String courseCode,Integer studentId) {
        ServiceResponse<List<Course>> response = courseService.findCourseByColumn("course_code", courseCode);
        if (response.getCode() != 200){
            return ServiceResponse.createEmptyResponse("邀请码不存在");
        }

        totalMapper.insert(new Total(response.getData().get(0).getCourseId(),studentId));
        return ServiceResponse.createResponse("加入课程成功");
    }

    /**
     *查询
     */
    public ServiceResponse<List<Total>> findTotalByMap(Map<String, Object> map){
        List<Total> totals = totalMapper.selectByMap(map);
        return ServiceResponse.createResponse("查询成功",totals);
    }

    public ServiceResponse<List<Total>> findTotalByColumn(String column,Object value){
        List<Total> totals = totalMapper.selectList(new QueryWrapper<Total>().like(column,value));
        return ServiceResponse.createResponse("查询成功",totals);
    }

    public ServiceResponse<List<Total>> findAll() {
        List<Total> totals = totalMapper.selectList(null);
        return ServiceResponse.createResponse("查询成功",totals);
    }

    /**
     *修改
     */
    public ServiceResponse<Total> modifyTotal(Total total){
        totalMapper.update(total, new QueryWrapper<Total>().eq("course_id", total.getCourseId()).eq("student_id", total.getStudentId()));
        return ServiceResponse.createResponse("修改成功");
    }

    /**
     *删除
     */
    public ServiceResponse<Total> deleteTotal(Integer courseId,Integer studentId){
        totalMapper.delete(new QueryWrapper<Total>().eq("course_id", courseId).eq("student_id", studentId));
        return ServiceResponse.createResponse("删除成功");
    }

}
