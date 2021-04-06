package com.jdk.projectinterface.controller;


import com.jdk.projectinterface.bean.Course;
import com.jdk.projectinterface.common.ServiceResponse;
import com.jdk.projectinterface.service.AccountService;
import com.jdk.projectinterface.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService  courseService;

    @Autowired
    AccountService accountService;

    @GetMapping("/addCourse")
    public Object addCourse(
            @RequestParam("teacherId") Integer teacherId,
            @RequestParam("name") String name,
            @RequestParam("avatar") String avatar,
            @RequestParam("introduce") String introduce
    ){
        Course course = new Course(teacherId,name,avatar,introduce);
        Map<String, Object> map = new HashMap<>();
        map.put("teacher_id",teacherId);
        map.put("course_name",name);
        if (courseService.findCourseByMap(map).getData().size() != 0){
            return ServiceResponse.createEmptyResponse("创建课程已存在");
        }
        return courseService.addCourse(course);
    }

    /**
     * 通过模糊查询课程表
     * @return 返回数组
     */
    @GetMapping("/findCourseByColumn")
    public Object findCourseByColumn(
            @RequestParam("column") String column,
            @RequestParam("value") Object value
    ){
        return courseService.findCourseByColumn(column,value);
    }

    /**
     * 通过模糊查询课程表
     * @return 返回数组
     */
    @GetMapping("/findCourseByMap")
    public Object findCourseByMap(@RequestParam Map<String, Object> map){
        return courseService.findCourseByMap(map);
    }

    /**
     * 修改课程，如果修改后的名称在数据库重复，取消修改操作
     */
    @GetMapping("/modifyCourse")
    public Object modifyCourse(
            @RequestParam("courseId") Integer courseId,
            @RequestParam("teacherId") Integer teacherId,
            @RequestParam("name") String courseName,
            @RequestParam("avatar") String courseAvatar,
            @RequestParam("introduce") String courseIntroduce
    ){
        Course courseOld = courseService.findCourseByColumn("course_id", courseId).getData().get(0);
        if (!courseOld.getCourseName().equals(courseName)){
            Map<String, Object> map = new HashMap<>();
            map.put("teacher_id",teacherId);
            map.put("course_name",courseName);
            if (courseService.findCourseByMap(map).getData().size() != 0){
                return ServiceResponse.createEmptyResponse("修改课程内容重复");
            }
        }
        Course course = new Course(teacherId, courseName, courseAvatar, courseIntroduce);
        course.setCourseId(courseId);
        return courseService.modifyCourse(course);
    }

    /**
     * 删除课程，如果课程关联的外键还有数据则不允许删除
     * @param courseId 课程的id
     * @param password 创建课程老师的登录密码
     */
    @GetMapping("/deleteCourse")
    public Object deleteCourse(
            @RequestParam("id") Integer courseId,
            @RequestParam("password") String password
    ){
        Course course = courseService.findCourseByColumn("course_id",courseId).getData().get(0);
        if (!password.equals(accountService.findTeacher("teacher_id",course.getTeacherId()).getData().get(0).getTeacherPassword())){
            return ServiceResponse.createFailResponse("密码错误！！！");
        }
        return courseService.deleteCourse(courseId);
    }
}
