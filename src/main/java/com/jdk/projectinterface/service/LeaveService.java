package com.jdk.projectinterface.service;

import com.jdk.projectinterface.mapper.LeaveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveService {
    @Autowired
    LeaveMapper leaveMapper;


}
