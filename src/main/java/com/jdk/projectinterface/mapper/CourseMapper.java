package com.jdk.projectinterface.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdk.projectinterface.bean.Course;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseMapper extends BaseMapper<Course> {
}
