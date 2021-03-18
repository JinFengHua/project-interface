package com.jdk.projectinterface.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
    private Boolean attendState;

    public Attend(Integer courseId, Timestamp attendStart, Timestamp attendEnd, Double attendLongitude, Double attendLatitude) {
        this.courseId = courseId;
        this.attendStart = attendStart;
        this.attendEnd = attendEnd;
        this.attendLongitude = attendLongitude;
        this.attendLatitude = attendLatitude;
    }
}
