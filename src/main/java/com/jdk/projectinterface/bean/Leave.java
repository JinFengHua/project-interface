package com.jdk.projectinterface.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private Integer teacherId;
    private Integer studentId;
    private Integer courseId;
    private Timestamp leaveTime;
    private Timestamp backTime;
    private String leaveReason;
    private Timestamp approvalTime;
    private Integer approvalResult;
    private String approvalRemark;

    public Leave(Integer teacherId, Integer studentId, Integer courseId, Timestamp leaveTime, Timestamp backTime, String leaveReason) {
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.leaveTime = leaveTime;
        this.backTime = backTime;
        this.leaveReason = leaveReason;
    }
}
