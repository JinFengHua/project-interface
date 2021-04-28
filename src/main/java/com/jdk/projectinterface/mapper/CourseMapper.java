package com.jdk.projectinterface.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdk.projectinterface.bean.Course;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMapper extends BaseMapper<Course> {

//    联表查询
    List<Course> findTeacherAllCourse(Integer teacherId);

    Course findCourseByCode(String code);

    List<Course> findAllCourse();

    List<Course> findTeacherAllCourseWithName(Integer teacherId, String name);
}
