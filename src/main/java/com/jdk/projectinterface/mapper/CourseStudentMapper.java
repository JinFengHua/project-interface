package com.jdk.projectinterface.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdk.projectinterface.bean.CourseStudent;
import com.jdk.projectinterface.bean.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseStudentMapper extends BaseMapper<CourseStudent> {
    List<CourseStudent> findStudentByCourseId(Integer courseId);
}
