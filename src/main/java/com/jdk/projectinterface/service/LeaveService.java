package com.jdk.projectinterface.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdk.projectinterface.bean.Leave;
import com.jdk.projectinterface.common.ServiceResponse;
import com.jdk.projectinterface.mapper.LeaveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LeaveService {
    @Autowired
    LeaveMapper leaveMapper;


    public ServiceResponse<Leave> addLeave(Leave leave) {
        leaveMapper.insert(leave);
        return ServiceResponse.createResponse("创建成功");
    }

    public ServiceResponse<List<Leave>> findLeaveByMap(Map<String, Object> map) {
        List<Leave> leaves = leaveMapper.selectByMap(map);
        return ServiceResponse.createResponse("查询成功",leaves);
    }

    public ServiceResponse<List<Leave>> findLeaveByColumn(String column,Object value) {
        List<Leave> leaves = leaveMapper.selectList(new QueryWrapper<Leave>().like(column,value));
        return ServiceResponse.createResponse("查询成功",leaves);
    }

    public ServiceResponse<Leave> modifyLeave(Leave leave) {
        leaveMapper.updateById(leave);
        return ServiceResponse.createResponse("审批成功");
    }

    public ServiceResponse<Leave> deleteLeave(Integer leaveId) {
        leaveMapper.deleteById(leaveId);
        return ServiceResponse.createResponse("删除成功");
    }
}
