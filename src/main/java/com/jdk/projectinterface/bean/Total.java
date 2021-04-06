package com.jdk.projectinterface.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程学生统计表
 */
@Data
@NoArgsConstructor
public class Total {
    private Integer courseId;
    private Integer studentId;
    private Integer numberSuccess;
    private Integer numberFail;
    private Integer numberAbsent;
    private Integer numberLeave;
    private Integer numberTotal;

    public Total(Integer courseId, Integer studentId) {
        this.courseId = courseId;
        this.studentId = studentId;
    }
}
