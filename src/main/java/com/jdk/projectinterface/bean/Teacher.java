package com.jdk.projectinterface.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Teacher {
    @TableId(type = IdType.AUTO)
    private Integer teacherId;
    private Integer adminId;
    private String teacherAccount;
    private String teacherPassword;
    private String teacherName;
    private Boolean teacherSex;
    private String teacherPhone;
    private String teacherEmail;
    private String teacherAvatar;

    public Teacher(Integer adminId, String teacherAccount, String teacherPassword, String teacherName, Boolean teacherSex, String teacherPhone, String teacherEmail, String teacherAvatar) {
        this.adminId = adminId;
        this.teacherAccount = teacherAccount;
        this.teacherPassword = teacherPassword;
        this.teacherName = teacherName;
        this.teacherSex = teacherSex;
        this.teacherPhone = teacherPhone;
        this.teacherEmail = teacherEmail;
        this.teacherAvatar = teacherAvatar;
    }
}