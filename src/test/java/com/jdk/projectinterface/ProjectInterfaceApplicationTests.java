package com.jdk.projectinterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jdk.projectinterface.bean.*;
import com.jdk.projectinterface.common.ServiceResponse;
import com.jdk.projectinterface.mapper.CourseMapper;
import com.jdk.projectinterface.mapper.CourseStudentMapper;
import com.jdk.projectinterface.mapper.LeaveMapper;
import com.jdk.projectinterface.mapper.RecordMapper;
import com.jdk.projectinterface.service.AccountService;
import com.jdk.projectinterface.service.CourseService;
import com.jdk.projectinterface.service.CourseStudentService;
import com.jdk.projectinterface.service.RecordService;
import com.jdk.projectinterface.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.sql.Timestamp;
import java.util.List;

@SpringBootTest
class ProjectInterfaceApplicationTests {

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    CourseService courseService;

    @Autowired
    AccountService accountService;

    @Autowired
    CourseStudentService courseStudentService;

    @Autowired
    CourseStudentMapper courseStudentMapper;

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
        List<CourseStudent> courseIdList = courseStudentMapper.selectList(new QueryWrapper<CourseStudent>().eq("student_id", 1));
        System.out.println(courseIdList.size());
    }

    @Test
    void usePythonTest(){
        String path = new File("").getAbsolutePath() + "\\src\\python\\face.exe";
        String path1 = "src/main/resources/static/face/4.png";
        String path2 = "src/main/resources/static/check/4_16.png";
        String[] args = new String[]{path , path1 , path2};
        StringBuffer sb = new StringBuffer();
        Process process = null;
        try {
            process = new ProcessBuilder(args).start();
            System.out.println(process.isAlive());
            int exitVal = process.waitFor();
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();

            System.out.println(exitVal);
            System.out.println(process.isAlive());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(sb.length());
    }

    @Test
    public void test(){
        String path1 = "src/main/resources/static/face/4.png";
        String path2 = "src/main/resources/static/check/4_16.png";
        StringBuffer sb = new StringBuffer();
        try {
            String[] args = new String[]{"F:\\Anaconda\\envs\\face\\python.exe","F:/graduation_project/project_face/face.py" , path1 , path2};
            Process process = Runtime.getRuntime().exec(args);
            process.waitFor();
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(sb);
    }

}
