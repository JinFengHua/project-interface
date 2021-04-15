package com.jdk.projectinterface.controller;

import com.jdk.projectinterface.bean.CourseStudent;
import com.jdk.projectinterface.service.CourseStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/courseStudent")
public class CourseStudentController {
    @Autowired
    CourseStudentService courseStudentService;

    /**
     *添加
     */
    @GetMapping("/addCourseStudent")
    public Object addCourseStudent(
            @RequestParam("courseCode") String courseCode,
            @RequestParam("studentId") Integer studentId
    ){
        return courseStudentService.addCourseStudent(courseCode,studentId);
    }

    /**
     *查询
     */
    @GetMapping("/findCourseStudentByMap")
    public Object findCourseStudent(@RequestParam Map<String, Object> map){
        return courseStudentService.findCourseStudentByMap(map);
    }

    @GetMapping("/findCourseStudentByColumn")
    public Object findCourseStudentByColumn(
            @RequestParam("column") String column,
            @RequestParam("value") Object value
    ){
        return courseStudentService.findCourseStudentByColumn(column,value);
    }

    /**
     * 查找属于某个课程的所有学生
     */
    @GetMapping("/findAllByCourseId")
    public Object findAll(@RequestParam("courseId") Integer courseId){
        return courseStudentService.findAllByCourseId(courseId);
    }

    /**
     *修改
     */
    @GetMapping("/modifyCourseStudent")
    public Object modifyCourseStudent(
            @RequestParam("courseId") Integer courseId,
            @RequestParam("studentId") Integer studentId
    ){
        CourseStudent total = new CourseStudent(courseId,studentId);
        return courseStudentService.modifyCourseStudent(total);
    }

    /**
     *删除
     */
    @GetMapping("/deleteCourseStudent")
    public Object deleteCourseStudent(
            @RequestParam("courseId") Integer courseId,
            @RequestParam("studentId") Integer studentId
    ){
        return courseStudentService.deleteCourseStudent(courseId,studentId);
    }
}
