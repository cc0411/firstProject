package com.firstProject.service.Impl;


import com.firstProject.common.ServerResponse;
import com.firstProject.dao.ActivityMapper;
import com.firstProject.dao.Activity_memberMapper;
import com.firstProject.pojo.Activity_memberKey;
import com.firstProject.service.IActivity_memberKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iActivity_memberKeyService")
public class Activity_memberKeyImpl implements IActivity_memberKeyService {

    @Autowired
    private Activity_memberMapper activity_memberMapper;

    @Autowired
    private ActivityMapper activityMapper;

    //报名活动
    public ServerResponse signUp(Integer userId, Integer activityId, Activity_memberKey activity_memberKey){
        int count=activityMapper.selectActivity(activityId);
        Activity_memberKey activity_memberKey1=activity_memberMapper.selectByActivityIdUserId(activityId,userId);
        if(activity_memberKey1 != null){
            return ServerResponse.createByErrorMessage("请勿重复报名");
        }
        if(count>0) {
            activity_memberKey.setUserId(userId);
            activity_memberKey.setActivityId(activityId);
            int row=activity_memberMapper.insert(activity_memberKey);
            if(row>0){
                return ServerResponse.createBySuccess("报名成功");
            }
            return ServerResponse.createByErrorMessage("报名失败");
        }
        return ServerResponse.createByErrorMessage("该活动不存在");
    }

//取消报名
    public ServerResponse cancelSignUp(Integer userId, Integer activityId){
        Activity_memberKey activity_memberKey1=activity_memberMapper.selectByActivityIdUserId(activityId,userId);
        if(activity_memberKey1!=null){
            activity_memberKey1.setUserId(userId);
            activity_memberKey1.setActivityId(activityId);
            int row=activity_memberMapper.deleteByPrimaryKey(activity_memberKey1);
            if(row>0){
                return ServerResponse.createBySuccess("取消成功");
            }
            return ServerResponse.createByErrorMessage("取消失败");
        }
        return ServerResponse.createByErrorMessage("该用户未报名活动");
    }
}