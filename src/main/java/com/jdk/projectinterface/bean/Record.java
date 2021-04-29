package com.jdk.projectinterface.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 考勤记录表
 */
@Data
@NoArgsConstructor
public class Record {
    private Integer attendId;
    private Integer studentId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp recordTime;
    private String recordLocation;
    /**
     * 0代表未签，1代表失败，2代表成功,3代表请假
     */
    private Integer recordResult;
    private String recordPhoto;

    @TableField(exist = false)
    private Student student;
    @TableField(exist = false)
    private Attend attend;

    public Record(Integer attendId, Integer studentId, Timestamp recordTime, String recordLocation, String recordPhoto) {
        this.attendId = attendId;
        this.studentId = studentId;
        this.recordTime = recordTime;
        this.recordLocation = recordLocation;
        this.recordPhoto = recordPhoto;
    }

    public Record(Integer attendId, Integer studentId, Integer recordResult) {
        this.attendId = attendId;
        this.studentId = studentId;
        this.recordResult = recordResult;
    }
}
