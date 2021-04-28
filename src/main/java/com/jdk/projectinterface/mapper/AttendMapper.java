package com.jdk.projectinterface.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdk.projectinterface.bean.Attend;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface AttendMapper extends BaseMapper<Attend> {

    List<Attend> findNeedLeaveAttend(Integer courseId, Timestamp start, Timestamp end);

    List<Attend> findStudentAttend(Integer courseId, Timestamp joinTime);

    List<Attend> findStudentAttendByTime(Integer courseId, Timestamp joinTime, String time);

    List<Attend> findAttendByTime(Integer courseId, String time);

}
