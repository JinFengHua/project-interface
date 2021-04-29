package com.jdk.projectinterface.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 课程表
 */
@Data
@NoArgsConstructor
public class Course {
    @TableId(type = IdType.AUTO)
    private Integer courseId;
    private Integer teacherId;
    private String courseName;
    private String courseAvatar;
    private String courseIntroduce;
    private String courseCode;

    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp joinTime;

    //测试连表查询
    @TableField(exist = false)
    private Teacher teacher;

    public Course(Integer teacherId, String courseName, String courseAvatar, String courseIntroduce) {
        this.teacherId = teacherId;
        this.courseName = courseName;
        this.courseAvatar = courseAvatar;
        this.courseIntroduce = courseIntroduce;
    }
}
