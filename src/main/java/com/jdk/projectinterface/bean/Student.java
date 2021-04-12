package com.jdk.projectinterface.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 学生用户表
 */
@Data
@NoArgsConstructor
public class Student {
    @TableId(type = IdType.AUTO)
    private Integer studentId;
    private String studentAccount;
    private String studentPassword;
    private String studentName;
    private Boolean studentSex;
    private String studentAvatar;
    private String studentClass;
    private String studentFace;
    private String studentPhone;
    private String studentEmail;

    @TableField(exist = false)
    private List<Record> records;
    @TableField(exist = false)
    private List<Leave> leaves;

    public Student(String studentAccount, String studentPassword, String studentName, Boolean studentSex, String studentAvatar, String studentClass, String studentPhone, String studentEmail) {
        this.studentAccount = studentAccount;
        this.studentPassword = studentPassword;
        this.studentName = studentName;
        this.studentSex = studentSex;
        this.studentAvatar = studentAvatar;
        this.studentClass = studentClass;
        this.studentPhone = studentPhone;
        this.studentEmail = studentEmail;
    }
}
