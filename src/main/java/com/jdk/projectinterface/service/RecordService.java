package com.jdk.projectinterface.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdk.projectinterface.bean.Attend;
import com.jdk.projectinterface.bean.Record;
import com.jdk.projectinterface.common.ServiceResponse;
import com.jdk.projectinterface.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecordService {
    @Autowired
    RecordMapper recordMapper;

    @Autowired
    AttendService attendService;

    /**
     * 学生签到
     */
    public ServiceResponse<Record> addRecord(Record record) {
        recordMapper.insert(record);
        if (record.getRecordResult() == 3){
            return ServiceResponse.createResponse(record.getRecordRemark());
        } else {
            return ServiceResponse.createResponse(record.getRecordRemark());
        }
    }

    public ServiceResponse<Record> modifyRecord(Record record){

        recordMapper.update(record, new QueryWrapper<Record>().eq("attend_id", record.getAttendId()).eq("student_id", record.getStudentId()));
        return ServiceResponse.createResponse("修改成功");
    }

    /**
     * 查找
     */
    public ServiceResponse<List<Record>> findRecordByMap(Map<String,Object> map) {
        List<Record> records = recordMapper.selectByMap(map);
        return ServiceResponse.createResponse("查找成功",records);
    }

    public ServiceResponse<List<Record>> findRecordByColumn(String column,Object value){
        List<Record> records = recordMapper.selectList(new QueryWrapper<Record>().like(column, value));
        return ServiceResponse.createResponse("查找成功",records);
    }

    /**
     * 通过时间戳类型模糊查询
     */
    public ServiceResponse<List<Record>> findRecordByTime(String value){
        List<Record> records = recordMapper.findRecordByTime(value);
        return ServiceResponse.createResponse("查询成功",records);
    }

    public Boolean inClass(Integer attendId,Double longitude,Double latitude){
        Map<String, Object> map = new HashMap<>();
        map.put("attend_id",attendId);
        ServiceResponse<List<Attend>> attend = attendService.findAttendByMap(map);
        Double attendLongitude = attend.getData().get(0).getAttendLongitude();
        Double attendLatitude = attend.getData().get(0).getAttendLatitude();
        Double distance = Math.sqrt(Math.pow((attendLatitude - latitude),2) + Math.pow((attendLongitude - longitude),2));

        if (distance > 10){
            return false;
        }
        return true;
    }

    /**
     * 删除
     */
    public ServiceResponse<Record> deleteRecord(Integer attendId,Integer studentId){
        recordMapper.delete(new QueryWrapper<Record>().eq("attend_id", attendId).eq("student_id", studentId));
        return ServiceResponse.createResponse("删除成功");
    }

}
