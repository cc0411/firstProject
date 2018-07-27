package com.firstProject.service;

import com.firstProject.common.ServerResponse;
import com.firstProject.pojo.Activity_memberKey;

public interface IActivity_memberKeyService {

    ServerResponse signUp(Integer userId, Integer activityId, Activity_memberKey activity_memberKey);

    ServerResponse cancelSignUp(Integer userId, Integer activityId);
}