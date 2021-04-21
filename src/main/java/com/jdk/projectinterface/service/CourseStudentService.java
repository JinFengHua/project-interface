package com.jdk.projectinterface.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdk.projectinterface.bean.Course;
import com.jdk.projectinterface.bean.CourseStudent;
import com.jdk.projectinterface.bean.Student;
import com.jdk.projectinterface.common.ServiceResponse;
import com.jdk.projectinterface.mapper.CourseStudentMapper;
import com.jdk.projectinterface.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CourseStudentService {
    @Autowired
    CourseStudentMapper courseStudentMapper;

    @Autowired
    CourseService courseService;

    @Autowired
    StudentMapper studentMapper;

    /**
     *添加
     */
    public ServiceResponse<CourseStudent> addCourseStudent(String courseCode, Integer studentId) {
        List<Course> response = courseService.findCourseByColumn("course_code", courseCode).getData();
        if (response.size() == 0){
            return ServiceResponse.createEmptyResponse("邀请码不存在");
        }
        QueryWrapper<CourseStudent> query = new QueryWrapper<CourseStudent>().eq("course_id", response.get(0).getCourseId()).eq("student_id", studentId);
        Integer count = courseStudentMapper.selectCount(query);
        if (count != 0){
            return ServiceResponse.createEmptyResponse("已加入该课程");
        }

        courseStudentMapper.insert(new CourseStudent(response.get(0).getCourseId(),studentId));
	    CourseStudent courseStudent = courseStudentMapper.selectOne(query);
        return ServiceResponse.createResponse("加入课程成功",courseStudent);
    }

    /**
     *查询
     */
    public ServiceResponse<List<CourseStudent>> findCourseStudentByMap(Map<String, Object> map){
        List<CourseStudent> totals = courseStudentMapper.selectByMap(map);
        return ServiceResponse.createResponse("查询成功",totals);
    }

    public ServiceResponse<List<CourseStudent>> findCourseStudentByColumn(String column,Object value){
        List<CourseStudent> totals = courseStudentMapper.selectList(new QueryWrapper<CourseStudent>().like(column,value));
        return ServiceResponse.createResponse("查询成功",totals);
    }

    /**
     *修改
     */
    public ServiceResponse<CourseStudent> modifyCourseStudent(CourseStudent total){
        courseStudentMapper.update(total, new QueryWrapper<CourseStudent>().eq("course_id", total.getCourseId()).eq("student_id", total.getStudentId()));
        return ServiceResponse.createResponse("修改成功");
    }

    /**
     *删除
     */
    public ServiceResponse<CourseStudent> deleteCourseStudent(Integer courseId,Integer studentId){
        courseStudentMapper.delete(new QueryWrapper<CourseStudent>().eq("course_id", courseId).eq("student_id", studentId));
        return ServiceResponse.createResponse("删除成功");
    }

    public ServiceResponse<List<CourseStudent>> findAllByCourseId(Integer courseId) {
        /*List<CourseStudent> courseStudents = courseStudentMapper.selectList(new QueryWrapper<CourseStudent>().eq("course_id", courseId));
        List<Student> students = new ArrayList<>();
        for (CourseStudent courseStudent : courseStudents) {
            students.add(studentMapper.selectById(courseStudent.getStudentId()));
        }*/
        List<CourseStudent> students = courseStudentMapper.findStudentByCourseId(courseId);
        return ServiceResponse.createResponse("查询成功",students);
    }
}
