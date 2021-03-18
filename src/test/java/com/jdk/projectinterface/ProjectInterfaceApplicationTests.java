package com.jdk.projectinterface;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jdk.projectinterface.bean.Admini;
import com.jdk.projectinterface.bean.Total;
import com.jdk.projectinterface.mapper.TotalMapper;
import com.jdk.projectinterface.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SpringBootTest
class ProjectInterfaceApplicationTests {

    @Autowired
    TotalMapper totalMapper;

    @Test
    void contextLoads() {
        Utils.deleteImage("http://localhost:8080/image/checkimages/6c3467e4-0f6a-4f60-b0a3-14e9b7d13b92.jpg","checkimages");
    }

}
