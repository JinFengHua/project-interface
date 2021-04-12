package com.jdk.projectinterface;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jdk.projectinterface.bean.Course;
import com.jdk.projectinterface.bean.Leave;
import com.jdk.projectinterface.bean.Record;
import com.jdk.projectinterface.mapper.CourseMapper;
import com.jdk.projectinterface.mapper.LeaveMapper;
import com.jdk.projectinterface.mapper.RecordMapper;
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
    RecordMapper recordMapper;

    @Autowired
    LeaveMapper leaveMapper;

    @Test
    void insertTest(){
        List<Record> allRecord = recordMapper.findAllRecord(12);
        for (Record record : allRecord) {
            System.out.println(record.toString());
        }
    }

    @Test
    void contextLoads() {
        String imagePath = new File("").getAbsolutePath() + "\\src\\main\\resources\\static\\";
        System.out.println("file:" + imagePath);
    }

}
