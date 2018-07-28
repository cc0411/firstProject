package com.firstProject.service.Impl;

import com.firstProject.common.ServerResponse;
import com.firstProject.dao.ActivityMapper;
import com.firstProject.dao.Activity_memberMapper;
import com.firstProject.dao.ExchangeMapper;
import com.firstProject.dao.UserMapper;
import com.firstProject.pojo.Activity;
import com.firstProject.pojo.Activity_memberKey;
import com.firstProject.pojo.User;
import com.firstProject.service.IActivityService;
import com.firstProject.vo.ActivityVo;
import com.firstProject.vo.UserVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by 吴家俊 on 2018/7/24.
 */
@Service("iActivityService")
public class ActivityServiceImpl implements IActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Activity_memberMapper activity_memberMapper;

    public ServerResponse<String> release(Activity activity){
        //活动名称 时间 地点不能为空
        if(StringUtils.isBlank(activity.getName()) || StringUtils.isBlank(activity.getAddress()) || StringUtils.isBlank(activity.getTime()))
            return ServerResponse.createByErrorMessage("活动名称、时间、地点不能为空");
        int row = activityMapper.insertSelective(activity);
        if(row > 0){
            Activity_memberKey activity_memberKey = new Activity_memberKey(activity.getId(),activity.getUserId());
            activity_memberMapper.insert(activity_memberKey);
            return ServerResponse.createBySuccessMessage("发布活动成功");
        }
        return ServerResponse.createByErrorMessage("发布活动失败");
    }

    public ServerResponse search(String activityName,Integer activityId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        //去除多余的空格
        //activityName = activityName.trim();
        if(StringUtils.isBlank(activityName.trim()) && activityId == null){
            return ServerResponse.createByErrorMessage("搜索条件不能为空");
        }
        if(StringUtils.isNotBlank(activityName)){
            //模糊查询sql语句拼接
            activityName = new StringBuilder().append("%").append(activityName).append("%").toString();
        }else{
            activityName = null;
        }
        List<Activity> activityList = activityMapper.selectByNameAndActivityId(activityName,activityId);
        System.out.println(activityList.size());
        if(activityList.size() == 0){
            return ServerResponse.createBySuccessMessage("没有符合条件的活动");
        }
        List<ActivityVo> activityVoList = Lists.newArrayList();
        for (Activity activityItem : activityList){
            ActivityVo activityVo = this.assembleActivityVo(activityItem);
            activityVoList.add(activityVo);
        }
        PageInfo pageInfo = new PageInfo(activityList);
        pageInfo.setList(activityVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    private ActivityVo assembleActivityVo(Activity activity){
        ActivityVo activityVo = new ActivityVo();
        //获取活动基本信息
        activityVo.setActivityId(activity.getId());
        activityVo.setActivityImageurl(activity.getImageurl());
        activityVo.setActivityName(activity.getName());
        activityVo.setActivityTime(activity.getTime());
        activityVo.setAddress(activity.getAddress());
        activityVo.setStatus(1);
        activityVo.setIntroduction(activity.getIntroduction());
        //获取活动发起人信息
        User user = userMapper.selectByPrimaryKey(activity.getUserId());
        //得到发起人名字
        StringBuilder userNameAll = new StringBuilder(user.getName());
        //设置发起人信息
        activityVo.setCreaterSignature(user.getSignature());
        activityVo.setCreaterName(user.getName());
        activityVo.setCreaterImageurl(user.getImageurl());
        activityVo.setCreaterPhone(user.getPhone());
        //查询人数
        Integer number = activity_memberMapper.selectByActivityId(activity.getId());
        //设置人数信息
        activityVo.setPeopleNumber(number);
        //获取参加人的userId
        List<Integer> userIdList = activity_memberMapper.selectUserIdByActivityId(activity.getId());
        //组建userVoList
//        List<UserVo> userVoList = Lists.newArrayList();
//        for(Integer userId : userIdList){
//            UserVo userVo = this.assembleUserVo(userId);
//            userVoList.add(userVo);
//
//        }
//        activityVo.setUserVoList(userVoList);
        //生成并设置含所有人名字的字符串
        String name = new String();
        for(Integer userId : userIdList){
            name = this.assembleUserNameAll(userId);
            userNameAll.append(",").append(name);
        }
        activityVo.setUserNameAll(userNameAll.toString());
        return activityVo;
    }

    private UserVo assembleUserVo(Integer userId){
        UserVo userVo = new UserVo();
        User user = userMapper.selectByPrimaryKey(userId);
        userVo.setImageurl(user.getImageurl());
        userVo.setName(user.getName());
        userVo.setPhone(user.getPhone());
        userVo.setSignature(user.getSignature());
        userVo.setUserName(user.getUsername());
        return userVo;
    }

    private String assembleUserNameAll(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        return user.getName();
    }

    public ServerResponse<String> delete(int activityId,int userId){
        //先验证该活动是不是该用户的，防止横向越权
        int row = activityMapper.selectByActivityIdUserId(userId,activityId);
        System.out.println(row);
        if(row > 0){
            activityMapper.deleteByPrimaryKey(activityId);
            return ServerResponse.createBySuccessMessage("删除活动成功");
        }else{
            return ServerResponse.createByErrorMessage("用户不是该活动的发起者，无权删除");
        }
    }

    public ServerResponse uploadActivityPictureUrl(String url,int activityId){
        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setImageurl(url);
        int row =  activityMapper.updateByPrimaryKeySelective(activity);
        if(row > 0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }


    //   修改活动信息
    public ServerResponse updateInformation(Activity activity,int activityId,Integer userId){

        if(StringUtils.isBlank(activity.getName()) || StringUtils.isBlank(activity.getAddress()) || StringUtils.isBlank(activity.getTime())) {
            return ServerResponse.createByErrorMessage("名称、时间和地点均不可为空");
        }
        Activity updateActivity =activityMapper.selectActivityByActivityIdUserId(activityId,userId);
        if(updateActivity!=null){
            updateActivity.setAddress(activity.getAddress());
            updateActivity.setName(activity.getName());
            updateActivity.setTime(activity.getTime());
            updateActivity.setIntroduction(activity.getIntroduction());
            updateActivity.setImageurl(activity.getImageurl());
            int row = activityMapper.updateByPrimaryKey(updateActivity);
            if(row>0){
                return ServerResponse.createBySuccess("修改信息成功",updateActivity);
            }
            return  ServerResponse.createByErrorMessage("修改信息失败");
        }else{
            return ServerResponse.createByErrorMessage("该活动不存在");
        }

    }

    //    获取活动列表
    public ServerResponse getActivityList(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Activity> activityList = activityMapper.selectList();
        List<ActivityVo> activityVoList= Lists.newArrayList();
        for( Activity activityItem:activityList)
        {
            ActivityVo activityVo = assembleActivityVoYyj(activityItem);
            activityVoList.add(activityVo);
        }
        PageInfo pageResult = new PageInfo(activityList);
        pageResult.setList(activityVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    private ActivityVo assembleActivityVoYyj(Activity activity)
    {
        ActivityVo activityVo = new ActivityVo();
        activityVo.setActivityId(activity.getId());
        activityVo.setActivityName(activity.getName());
        activityVo.setAddress(activity.getAddress());
        activityVo.setIntroduction(activity.getIntroduction());
        activityVo.setActivityTime(activity.getTime());
        activityVo.setStatus(activity.getStatu());
        activityVo.setActivityImageurl(activity.getImageurl());
        User user= userMapper.selectUserId(activity.getUserId());
        activityVo.setCreaterSignature(user.getSignature());
        activityVo.setCreaterName(user.getName());
        activityVo.setCreaterImageurl(user.getImageurl());
        activityVo.setCreaterPhone(user.getPhone());
        int count= activity_memberMapper.selectByActivityId(activity.getId());
        activityVo.setPeopleNumber(count);
        List<String> userNameList = Lists.newArrayList();
        userNameList.add(activityVo.getCreaterName());
        List<Integer> userIdList = activity_memberMapper.selectUserIdByActivityId(activity.getId());
        for(Integer userId : userIdList){
            User user2= userMapper.selectByPrimaryKey(userId);
            userNameList.add(user2.getName());
        }
        String userName=listToString(userNameList,',');
        activityVo.setUserNameAll(userName);
        // activityVo.setUserVoList(userVoList);

        return activityVo;
    }
    public String listToString(List list, char separator) {
        return org.apache.commons.lang.StringUtils.join(list.toArray(),separator);
    }





}