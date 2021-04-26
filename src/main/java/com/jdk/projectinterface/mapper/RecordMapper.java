package com.jdk.projectinterface.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdk.projectinterface.bean.Record;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordMapper extends BaseMapper<Record> {

    List<Record> findRecordByTime(String recordTime);

//    查询某次考勤任务的所以签到记录
    List<Record> findAllRecord(Integer attendId);

    List<Record> findAllRecordWithAttend();
}
