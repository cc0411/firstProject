package com.firstProject.controller;

import com.firstProject.common.Const;
import com.firstProject.common.ServerResponse;
import com.firstProject.pojo.Activity;
import com.firstProject.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * created by 吴家俊 on 2018/7/23.
 */
@Controller
@RequestMapping("/activity/")
public class ActivityController {

    @RequestMapping("release.do")
    @ResponseBody
    public ServerResponse<String> release_activity(HttpSession session, Activity activity){
        User user = (User) session.getAttribute(Const.REGULAR_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }

    }
}