package com.firstProject.controller;

import com.firstProject.common.Const;
import com.firstProject.common.ResponseCode;
import com.firstProject.common.ServerResponse;
import com.firstProject.pojo.User;
import com.firstProject.service.IFileService;
import com.firstProject.service.IUserService;
import com.firstProject.utill.PropertiesUtil;
import com.firstProject.vo.ExchangeListVo;
import com.firstProject.vo.UserVo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.firstProject.common.Const.REGULAR_USER;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IFileService iFileService;
    @Autowired
    private IUserService iUserService;
    //登陆
    @RequestMapping(value="login.do")
    @ResponseBody
    public ServerResponse<User> login(HttpSession session, String userName, String password){
        ServerResponse<User> response=iUserService.login(userName,password);
        if(response.isSuccess()){
            session.setAttribute(REGULAR_USER,response.getData());
        }
        return response;
    }

    //注册
    @RequestMapping(value = "register.do")
    @ResponseBody
    public ServerResponse<String> register(String userName, String password, String name) {
        User user=new User();
        user.setUsername(userName);
        user.setPassword(password);
        user.setName(name);
        if(user.getPassword().length()<6){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.PASSWORD_IS_TOO_SHORT.getCode(),ResponseCode.PASSWORD_IS_TOO_SHORT.getDesc());
        }
        if (user.getUsername().length()==0||user.getName().length()==0){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR_MASSAGE.getCode(), ResponseCode.ERROR_MASSAGE.getDesc());
        }
        return iUserService.register(user);
    }


    //用户注销
    @RequestMapping(value ="logout.do")
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.REGULAR_USER);
        return ServerResponse.createBySuccess();
    }

    //我参与的活动列表
    @RequestMapping(value ="activity_list.do")
    @ResponseBody
    public ServerResponse activityList(HttpSession session, @RequestParam(value = "pageNum" , defaultValue = "1") int pageNum,
                                       @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user=(User)session.getAttribute(Const.REGULAR_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        //查询activity列表
        return iUserService.activityList(user.getId(),pageNum,pageSize);
    }

    //我发布的活动列表
    @RequestMapping(value = "my_activity_list.do")
    @ResponseBody
    public ServerResponse MyActivityList(HttpSession session, @RequestParam(value = "pageNum" , defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user=(User)session.getAttribute(Const.REGULAR_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iUserService.myActivityList(user.getId(),pageNum,pageSize);
    }


//    @RequestMapping("upload.do")
//    @ResponseBody
//    public ServerResponse upload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
//            String path = request.getSession().getServletContext().getRealPath("upload");
//            String targetFileName = iFileService.upload(file,path);
//            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
//            Map fileMap = Maps.newHashMap();
//            fileMap.put("uri",targetFileName);
//            fileMap.put("url",url);
//            return ServerResponse.createBySuccess(fileMap);
//
//    }


    //获取用户信息
    @RequestMapping("get_information.do")
    @ResponseBody
    public ServerResponse<UserVo> get_information(HttpSession session){
        User currentUser = (User)session.getAttribute(Const.REGULAR_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录,需要强制登录status=10");
        }
        return iUserService.getInformation(currentUser.getId());
    }
    //修改用户信息
    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse<User> update(HttpSession session, User user){
        User currentUser = (User)session.getAttribute(Const.REGULAR_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setUsername(currentUser.getUsername());
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if(response.isSuccess()){
            response.getData().setUsername(currentUser.getUsername());
            session.setAttribute(Const.REGULAR_USER,response.getData());
        }
        return response;
    }
    //修改图片
    @RequestMapping("uploading.do")
    @ResponseBody
    public ServerResponse upload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        User user = (User)session.getAttribute(Const.REGULAR_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }

        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file,path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;

        Map fileMap = Maps.newHashMap();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);
        user.setImageurl(url);


        return ServerResponse.createBySuccess(fileMap);

    }

    //修改密码
    @RequestMapping("reset_password.do")
    @ResponseBody
    public ServerResponse<String> setpassword(HttpSession session, String passwordOld, String passwordNew){
        User user = (User)session.getAttribute(Const.REGULAR_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登陆");
        }
        return  iUserService.resetPassword(passwordOld,passwordNew,user);
    }
    //交换信息
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<ExchangeListVo> list(HttpSession session){
        User user = (User)session.getAttribute(Const.REGULAR_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登陆");
        }
        return  iUserService.list(user.getId());
    }

    //我的换出
    @RequestMapping("exchangeout.do")
    @ResponseBody
    public ServerResponse<ExchangeListVo> Exchangeout(HttpSession session){
        User user = (User)session.getAttribute(Const.REGULAR_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登陆");
        }
        return  iUserService.ExchangeOut(user.getId());
    }

    //我的换入

    @RequestMapping("exchangein.do")
    @ResponseBody
    public ServerResponse<ExchangeListVo> ExchangeIn(HttpSession session){
        User user = (User)session.getAttribute(Const.REGULAR_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登陆");
        }
        return  iUserService.ExchangeIn(user.getId());
    }

}
