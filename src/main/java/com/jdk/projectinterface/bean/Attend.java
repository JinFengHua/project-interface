package com.jdk.projectinterface.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 考勤任务表
 */
@Data
@NoArgsConstructor
public class Attend {
    @TableId(type = IdType.AUTO)
    private Integer attendId;
    private Integer courseId;
    private Timestamp attendStart;
    private Timestamp attendEnd;
    private Double attendLongitude;
    private Double attendLatitude;
    private String attendLocation;

    public Attend(Integer courseId, Timestamp attendStart, Timestamp attendEnd, Double attendLongitude, Double attendLatitude, String attendLocation) {
        this.courseId = courseId;
        this.attendStart = attendStart;
        this.attendEnd = attendEnd;
        this.attendLongitude = attendLongitude;
        this.attendLatitude = attendLatitude;
        this.attendLocation = attendLocation;
    }
}
