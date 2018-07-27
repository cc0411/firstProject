package com.firstProject.service;

import com.firstProject.common.ServerResponse;
import com.firstProject.pojo.User;
import com.firstProject.vo.ExchangeListVo;
import com.firstProject.vo.UserVo;
import com.github.pagehelper.PageInfo;

public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);
    //用户参加的活动
    ServerResponse<PageInfo> activityList(Integer userId, int pageNum, int pageSize);
    //用户发布的活动
    ServerResponse<PageInfo> myActivityList(Integer userId, int pageNum, int pageSize);




    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);//user
    ServerResponse<UserVo> getInformation(Integer userId);////user
    ServerResponse<User> updateInformation(User user);//user
    ServerResponse<ExchangeListVo> ExchangeOut(Integer userId);//user
    ServerResponse<ExchangeListVo> ExchangeIn(Integer userId);//user
    ServerResponse<ExchangeListVo> list(Integer userId);//user

}
