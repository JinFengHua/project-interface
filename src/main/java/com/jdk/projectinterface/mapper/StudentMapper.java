package com.jdk.projectinterface.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdk.projectinterface.bean.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper extends BaseMapper<Student> {
}
