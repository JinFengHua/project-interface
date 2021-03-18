package com.jdk.projectinterface.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@TableName("leave_attend")
@NoArgsConstructor
public class Leave {
    private Integer studentId;
    private Integer attendId;
    private Timestamp leaveTime;
    private Timestamp backTime;
    private String leaveReason;
    private Timestamp approvalTime;
    private Integer approvalResult;
    private String approvalRemark;
}
