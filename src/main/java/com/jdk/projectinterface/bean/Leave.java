package com.jdk.projectinterface.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 请假表
 */
@Data
@TableName("leave_attend")
@NoArgsConstructor
public class Leave {
    @TableId(type = IdType.AUTO)
    private Integer leaveId;
    private Integer studentId;
    private Integer courseId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp leaveTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp backTime;
    private String leaveReason;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp approvalTime;
    private Integer approvalResult;
    private String approvalRemark;

    @TableField(exist = false)
    private Student student;

    public Leave(Integer studentId, Integer courseId, Timestamp leaveTime, Timestamp backTime, String leaveReason) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.leaveTime = leaveTime;
        this.backTime = backTime;
        this.leaveReason = leaveReason;
    }
}
