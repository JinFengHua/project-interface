package com.jdk.projectinterface.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Statistics {
    private String studentName;
    private String studentAccount;
    private Integer absentCount;
    private Integer failedCount;
    private Integer successCount;
    private Integer leaveCount;
}
