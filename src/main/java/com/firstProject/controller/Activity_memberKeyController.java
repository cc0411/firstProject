package com.firstProject.controller;

import com.firstProject.common.Const;
import com.firstProject.common.ResponseCode;
import com.firstProject.common.ServerResponse;
import com.firstProject.pojo.Activity_memberKey;
import com.firstProject.pojo.User;
import com.firstProject.service.IActivity_memberKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/activity_member/")

public class Activity_memberKeyController {

    @Autowired
    private IActivity_memberKeyService iActivity_memberKeyService;

    @RequestMapping(value = "sign_up.do")
    @ResponseBody
    public ServerResponse signUp(HttpSession session, Integer activityId, Activity_memberKey activity_memberKey){
        User user = (User) session.getAttribute(Const.REGULAR_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iActivity_memberKeyService.signUp(user.getId(),activityId,activity_memberKey);
    }


    @RequestMapping(value = "cancel_sign_up.do")
    @ResponseBody
    public ServerResponse cancelSignUp(HttpSession session, Integer activityId){
        User user = (User) session.getAttribute(Const.REGULAR_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iActivity_memberKeyService.cancelSignUp(user.getId(),activityId);
    }
}