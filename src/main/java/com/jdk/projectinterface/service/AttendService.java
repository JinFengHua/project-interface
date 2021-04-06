package com.jdk.projectinterface.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdk.projectinterface.bean.Attend;
import com.jdk.projectinterface.common.ServiceResponse;
import com.jdk.projectinterface.mapper.AttendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AttendService {
    @Autowired
    AttendMapper attendMapper;

    /**
     * 添加
     */
    public ServiceResponse<Attend> addAttend(Attend attend) {
        attendMapper.insert(attend);
        return ServiceResponse.createResponse("创建成功");
    }

    /**
     * 修改
     */
    public ServiceResponse<Attend> modifyAttend(Attend attend){
        attendMapper.updateById(attend);
        return ServiceResponse.createResponse("修改成功");
    }

    /**
     * 查询
     */
    public ServiceResponse<List<Attend>> findAttendByColumn(String column,Object value){
        List<Attend> attends = attendMapper.selectList(new QueryWrapper<Attend>().like(column, value));
        return ServiceResponse.backFailResponse("查询结果为空","查询成功", attends);
    }

    public ServiceResponse<List<Attend>> findAttendByMap(Map<String, Object> map){
        List<Attend> attends = attendMapper.selectByMap(map);
        return ServiceResponse.backFailResponse("查询结果为空","查询成功", attends);
    }

    /**
     * 删除
     */
    public ServiceResponse<Attend> deleteAttend(Integer attendId){
        attendMapper.deleteById(attendId);
        return ServiceResponse.createResponse("删除成功");
    }
}
