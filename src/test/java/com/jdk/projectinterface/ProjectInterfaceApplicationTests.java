package com.jdk.projectinterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jdk.projectinterface.bean.Course;
import com.jdk.projectinterface.bean.Leave;
import com.jdk.projectinterface.bean.Record;
import com.jdk.projectinterface.bean.Statistics;
import com.jdk.projectinterface.common.ServiceResponse;
import com.jdk.projectinterface.mapper.CourseMapper;
import com.jdk.projectinterface.mapper.LeaveMapper;
import com.jdk.projectinterface.mapper.RecordMapper;
import com.jdk.projectinterface.service.AccountService;
import com.jdk.projectinterface.service.CourseService;
import com.jdk.projectinterface.service.RecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

@SpringBootTest
class ProjectInterfaceApplicationTests {

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    AccountService accountService;

    @Autowired
    CourseService courseService;

    @Autowired
    RecordMapper recordMapper;

    @Autowired
    RecordService service;

    @Test
    void insertTest(){
        List<Record> allRecord = recordMapper.findAllRecord(12);
        for (Record record : allRecord) {
            System.out.println(record.toString());
        }
    }

    @Test
    void contextLoads() {
        ServiceResponse<List<Course>> courseByStudentId = courseService.findCourseByStudentId(1);
        String s = courseByStudentId.toString();
        System.out.println(JSONObject.parse(s));
    }

}
