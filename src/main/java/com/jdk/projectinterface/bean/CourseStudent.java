package com.jdk.projectinterface.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * 课程学生统计表
 */
@Data
@NoArgsConstructor
public class CourseStudent {
    private Integer courseId;
    private Integer studentId;
    private Timestamp joinTime;

    @TableField(exist = false)
    private Student student;

    public CourseStudent(Integer courseId, Integer studentId) {
        this.courseId = courseId;
        this.studentId = studentId;
    }
}
