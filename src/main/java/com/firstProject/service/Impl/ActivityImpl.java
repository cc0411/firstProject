package com.firstProject.service.Impl;

import com.firstProject.common.ServerResponse;
import com.firstProject.dao.UserMapper;
import com.firstProject.pojo.Activity;
import com.firstProject.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by 吴家俊 on 2018/7/24.
 */
@Service("iActivityService")
public class ActivityImpl implements IActivityService {

    @Autowired
    private UserMapper userMapper;

    public ServerResponse<String> release(Activity activity){

    }
}