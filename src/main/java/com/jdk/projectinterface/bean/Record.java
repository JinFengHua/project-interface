package com.jdk.projectinterface.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class Record {
    private Integer attendId;
    private Integer studentId;
    private Timestamp recordTime;
    private Double recordLongitude;
    private Double recordLatitude;
    private Integer recordResult;
    private String recordRemark;
    private String recordPhoto;

    public Record(Integer attendId, Integer studentId, Timestamp recordTime, Double recordLongitude, Double recordLatitude,String recordPhoto) {
        this.attendId = attendId;
        this.studentId = studentId;
        this.recordTime = recordTime;
        this.recordLongitude = recordLongitude;
        this.recordLatitude = recordLatitude;
        this.recordPhoto = recordPhoto;
    }
}
