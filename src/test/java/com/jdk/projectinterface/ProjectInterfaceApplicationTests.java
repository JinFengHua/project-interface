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
        List<Course> allCourse = courseMapper.findAllCourse();
        for (Course course : allCourse) {
            System.out.println(course.toString());
        }
    }

    Double cal(String a,String b){
        String[] aSplit = a.split(",");
        String[] bSplit = b.split(",");
        Double x1 = Double.valueOf(aSplit[0]);
        Double y1 = Double.valueOf(aSplit[1]);
        Double x2 = Double.valueOf(bSplit[0]);
        Double y2 = Double.valueOf(bSplit[1]);
        return Math.sqrt(Math.pow((x1 - x2),2) + Math.pow((y1 - y2),2)) * 100000;
    }

}
