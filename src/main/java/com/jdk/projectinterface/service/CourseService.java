package com.jdk.projectinterface.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdk.projectinterface.bean.Course;
import com.jdk.projectinterface.bean.CourseStudent;
import com.jdk.projectinterface.common.ServiceResponse;
import com.jdk.projectinterface.mapper.CourseMapper;
import com.jdk.projectinterface.mapper.CourseStudentMapper;
import com.jdk.projectinterface.mapper.TeacherMapper;
import com.jdk.projectinterface.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {
    @Autowired
    CourseMapper courseMapper;

    @Autowired
    CourseStudentMapper courseStudentMapper;

    @Autowired
    TeacherMapper teacherMapper;

    public ServiceResponse addCourse(Course course) {
        String code;
        while (true){
            code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
            Course result = courseMapper.selectOne(new QueryWrapper<Course>().eq("course_code", code));
            if (Utils.isEmpty(result)) {
                break;
            }
        }
        course.setCourseCode(code);
        courseMapper.insert(course);
        if (course.getCourseId() != null) {
//            修改课程头像名称并重写入数据库
            String s = Utils.renameImage(course.getCourseAvatar(), "c" + course.getCourseId());
            course.setCourseAvatar(s);
            courseMapper.updateById(course);
            return ServiceResponse.createResponse("创建成功",code);
        }
        return ServiceResponse.createFailResponse("添加失败");
    }

    /**
     * 模糊查询
     */
    public ServiceResponse<List<Course>> findCourseByColumn(String column, Object value) {
        List<Course> courses = courseMapper.selectList(new QueryWrapper<Course>().like(column, value));
        return ServiceResponse.createResponse("查询成功",courses);
    }

    /**
     * 根据map里的值进行准确查找
     */
    public ServiceResponse<List<Course>> findCourseByMap(Map<String, Object> map){
        List<Course> courses = courseMapper.selectByMap(map);
        return ServiceResponse.createResponse("查询成功",courses);
    }

    public ServiceResponse<Course> modifyCourse(Course course) {
        courseMapper.updateById(course);
        return  ServiceResponse.createResponse("修改成功");
    }


    public ServiceResponse<Course> deleteCourse(Integer courseId) {
        courseMapper.deleteById(courseId);
        return ServiceResponse.createResponse("删除成功");
    }

    public ServiceResponse<Course> findCourseByCode(String code) {
        Course course = courseMapper.findCourseByCode(code);
        return ServiceResponse.createResponse("查询成功",course);
    }

    public ServiceResponse<List<Course>> findCourseByTeacherId(Integer teacherId){
        List<Course> courses = courseMapper.findTeacherAllCourse(teacherId);
        return ServiceResponse.createResponse("查询成功",courses);
    }



    public ServiceResponse<List<Course>> findCourseByStudentId(Integer studentId) {
        List<CourseStudent> courseIdList = courseStudentMapper.selectList(new QueryWrapper<CourseStudent>().eq("student_id", studentId));
        List<Course> courseList = new ArrayList<>();
        for (CourseStudent courseStudent : courseIdList) {
            Course course = courseMapper.selectById(courseStudent.getCourseId());
            course.setTeacher(teacherMapper.selectById(course.getTeacherId()));
            course.setJoinTime(courseStudent.getJoinTime());
            courseList.add(course);
        }
        return ServiceResponse.createResponse("查询成功",courseList);
    }

    public ServiceResponse<List<Course>> findAllCourse(){
        List<Course> courses = courseMapper.findAllCourse();
        return ServiceResponse.createResponse("查询成功",courses);
    }

    public ServiceResponse<List<Course>> findCourseByTeacherIdWithName(Integer teacherId, String name) {
        List<Course> courses = courseMapper.findTeacherAllCourseWithName(teacherId,name);
        return ServiceResponse.createResponse("查询成功",courses);
    }

    public ServiceResponse<List<Course>> findCourseByStudentIdWithName(Integer studentId, String name) {
        List<CourseStudent> courseIdList = courseStudentMapper.selectList(new QueryWrapper<CourseStudent>().eq("student_id", studentId));
        List<Course> courseList = new ArrayList<>();
        for (CourseStudent courseStudent : courseIdList) {
            Course course = courseMapper.selectOne(new QueryWrapper<Course>().eq("course_id",courseStudent.getCourseId()).like("course_name",name));
            if (Utils.isEmpty(course)){
                continue;
            }
            course.setTeacher(teacherMapper.selectById(course.getTeacherId()));
            course.setJoinTime(courseStudent.getJoinTime());
            courseList.add(course);
        }
        return ServiceResponse.createResponse("查询成功",courseList);
    }
}
