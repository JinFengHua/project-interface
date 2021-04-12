package com.jdk.projectinterface.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程学生统计表
 */
@Data
@NoArgsConstructor
public class CourseStudent {
    private Integer courseId;
    private Integer studentId;

    public CourseStudent(Integer courseId, Integer studentId) {
        this.courseId = courseId;
        this.studentId = studentId;
    }
}
