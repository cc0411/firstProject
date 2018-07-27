package com.firstProject.service.Impl;

import com.firstProject.common.ServerResponse;
import com.firstProject.dao.ActivityMapper;
import com.firstProject.dao.Activity_memberMapper;
import com.firstProject.dao.ExchangeMapper;
import com.firstProject.dao.UserMapper;
import com.firstProject.pojo.Activity;
import com.firstProject.pojo.Activity_memberKey;
import com.firstProject.pojo.Exchange;
import com.firstProject.pojo.User;
import com.firstProject.service.IFileService;
import com.firstProject.service.IUserService;
import com.firstProject.utill.MD5Util;
import com.firstProject.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iUserService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Activity_memberMapper activity_memberMapper;

    @Autowired
    private IFileService iFileService;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    ExchangeMapper exchangeMapper;

    //登录
    public ServerResponse<User> login(String username, String password){

        int resultCount1=userMapper.checkUsername(username);
        if(resultCount1==0){
            return ServerResponse.createByErrorMessage("此用户名不存在");
        }
        String md5password=MD5Util.MD5EncodeUtf8(password);
        User user=userMapper.selectLogin(username,md5password);
        if(user==null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
         user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登陆成功",user);
    }

    //注册
    public ServerResponse<String> register(User user){
        int resultCount1=userMapper.checkUsername(user.getUsername());
        if(resultCount1>0){
            return ServerResponse.createByErrorMessage("用户名已存在，注册失败");
        }
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount2=userMapper.insert(user);
        if(resultCount2==0){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    //获取用户参加的活动列表
    public ServerResponse<PageInfo> activityList(Integer userId, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Activity_memberKey>activity_memberKeyList=activity_memberMapper.selectByUserIdU(userId);
        List<Integer> integerList=Lists.newArrayList();//活动id的一个集合
        for(Activity_memberKey item:activity_memberKeyList){
            integerList.add(item.getActivityId());
        }
        List<ActivityVo>activityVoList=this.getActivityVoList(integerList);
        PageInfo resultPage=new PageInfo(activityVoList);
        //resultPage.setList(activityVoList);
        return ServerResponse.createBySuccess(resultPage);
    }

    //我发布的活动列表
    public ServerResponse<PageInfo> myActivityList(Integer userId, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Activity>activityList=activityMapper.selectByUserIdU(userId);
        List<Integer> integerList=Lists.newArrayList();//活动id的一个集合
        for(Activity item:activityList){
            integerList.add(item.getId());
        }
        List<ActivityVo>activityVoList=this.getActivityVoList(integerList);
        PageInfo resultPage=new PageInfo(activityList);
        resultPage.setList(activityVoList);
        return ServerResponse.createBySuccess(resultPage);
    }

//传activityId集合返回
    private List<ActivityVo> getActivityVoList(List<Integer> integerList){
        List<ActivityVo> activityVoList= Lists.newArrayList();
        for(Integer item:integerList){
            Activity activity=new Activity();
            ActivityVo activityVo=new ActivityVo();
            activity=activityMapper.selectByPrimaryKey(item);
            activityVo.setActivityName(activity.getName());
            activityVo.setActivityTime(activity.getTime());
            activityVo.setAddress(activity.getAddress());
            activityVo.setIntroduction(activity.getIntroduction());
            activityVo.setActivityImageurl(activity.getImageurl());
            activityVo.setStatus(activity.getStatu());

            int peopleNumber=activity_memberMapper.selectByActivityId(activity.getId());//参与人数
            activityVo.setPeopleNumber(peopleNumber);
            //发布人信息
           User user=userMapper.selectByPrimaryKey(activity.getUserId());
            activityVo.setCreaterName(user.getName());
            activityVo.setCreaterPhone(user.getPhone());
            activityVo.setCreaterImageurl(user.getImageurl());
            //参与人信息
            String userNameAll=this.getUserNameAll(activity.getId());
            activityVo.setUserNameAll(userNameAll);
            activityVoList.add(activityVo);
        }
        return activityVoList;
    }

    //获取参与人的信息
    private String getUserNameAll(Integer activityId){
        String userNameAll="";
        List<UserVo>userVoList=Lists.newArrayList();
        List<Activity_memberKey> activity_memberKeyList=activity_memberMapper.selectPepleByActivityIdU(activityId);
        int i=0;
        for(Activity_memberKey item:activity_memberKeyList){
            UserVo userVo=new UserVo();
            User user=userMapper.selectByPrimaryKey(item.getUserId());
          userNameAll=(i==activity_memberKeyList.size()-1)?(userNameAll+user.getName()):(userNameAll+user.getName()+",");
            i++;
        }
        return userNameAll;
    }












    public ServerResponse<User> updateInformation(User user){

        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setName(user.getName());
        updateUser.setPhone(user.getPhone());
        updateUser.setSignature(user.getSignature());


        updateUser.setImageurl(user.getImageurl());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount > 0){
            return ServerResponse.createBySuccess(updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");

    }
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user){
        //防止横向越权,要校验一下这个用户的旧密码,一定要指定是这个用户.因为我们会查询一个count(1),如果不指定id,那么结果就是true啦count>0;
        int resultCount = userMapper.checkPassword_U(MD5Util.MD5EncodeUtf8(passwordOld),user.getName());
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }

        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount > 0){
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");
    }


    public ServerResponse<UserVo> getInformation(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        UserVo userVo=new UserVo();
        userVo.setImageurl(user.getImageurl());
        userVo.setName(user.getName());
        userVo.setSignature(user.getSignature());
        userVo.setPhone(user.getPhone());
        return ServerResponse.createBySuccess(userVo);
    }
    public ServerResponse<ExchangeListVo> ExchangeIn(Integer userId){
        ExchangeListVo exchangeListVo =new ExchangeListVo();
        List<Exchange> exchangeList=exchangeMapper.selectExchangeByExchangeUserIdFinished_U(userId,1);
        List<ExchangeVoU> exchangeVoList= Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(exchangeList)){
            for (Exchange exchangeItem : exchangeList){
                ExchangeVoU exchangeVoU=new ExchangeVoU();
                exchangeVoU.setAddress(exchangeItem.getAddress());
                exchangeVoU.setExpect(exchangeItem.getExpect());
                exchangeVoU.setImageurl(exchangeItem.getImageurl());
                exchangeVoU.setName(exchangeItem.getName());
                exchangeVoU.setPrice(exchangeItem.getPrice());
                exchangeVoU.setStatu(exchangeItem.getStatu());
                exchangeVoU.setTime(exchangeItem.getTime());

                exchangeVoList.add(exchangeVoU);
            }
            exchangeListVo.setExchangeVoList(exchangeVoList);
        }
        return   ServerResponse.createBySuccess(exchangeListVo);
    }

    public ServerResponse<ExchangeListVo> ExchangeOut(Integer userId){
        ExchangeListVo exchangeListVo =new ExchangeListVo();
        List<Exchange> exchangeList=exchangeMapper.selectExchangeByUserId_U(userId);
        List<ExchangeVoU> exchangeVoList= Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(exchangeList)){
            for (Exchange exchangeItem : exchangeList){
                ExchangeVoU exchangeVoU=new ExchangeVoU();
                exchangeVoU.setAddress(exchangeItem.getAddress());
                exchangeVoU.setExpect(exchangeItem.getExpect());
                exchangeVoU.setImageurl(exchangeItem.getImageurl());
                exchangeVoU.setName(exchangeItem.getName());
                exchangeVoU.setPrice(exchangeItem.getPrice());
                exchangeVoU.setStatu(exchangeItem.getStatu());
                exchangeVoU.setTime(exchangeItem.getTime());

                exchangeVoList.add(exchangeVoU);
            }
            exchangeListVo.setExchangeVoList(exchangeVoList);
        }
        return   ServerResponse.createBySuccess(exchangeListVo);
    }

    public ServerResponse<ExchangeListVo> list(Integer userId){
        ExchangeListVo exchangeListVo=this.getExchengeListVo(userId);
        return  ServerResponse.createBySuccess(exchangeListVo);
    }

    private ExchangeListVo getExchengeListVo(Integer userId){
        ExchangeListVo exchangeListVo =new ExchangeListVo();
        List<Exchange> exchangeList=exchangeMapper.selectExchangeByUserId_U(userId);
        List<ExchangeVoU> exchangeVoList= Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(exchangeList)){
            for (Exchange exchangeItem : exchangeList){
                ExchangeVoU exchangeVo=new ExchangeVoU();
                exchangeVo.setAddress(exchangeItem.getAddress());
                exchangeVo.setExpect(exchangeItem.getExpect());
                exchangeVo.setImageurl(exchangeItem.getImageurl());
                exchangeVo.setName(exchangeItem.getName());
                exchangeVo.setPrice(exchangeItem.getPrice());
                exchangeVo.setStatu(exchangeItem.getStatu());
                exchangeVo.setTime(exchangeItem.getTime());

                exchangeVoList.add(exchangeVo);
            }
            exchangeListVo.setExchangeVoList(exchangeVoList);
        }
        return  exchangeListVo;
    }


}


