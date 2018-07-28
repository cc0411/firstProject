package com.firstProject.controller;

import com.firstProject.common.Const;
import com.firstProject.common.ResponseCode;
import com.firstProject.common.ServerResponse;
import com.firstProject.pojo.Activity;
import com.firstProject.pojo.User;
import com.firstProject.service.IActivityService;
import com.firstProject.service.IFileService;
import com.firstProject.utill.PropertiesUtil;
import com.firstProject.vo.ActivityVo;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by 吴家俊 on 2018/7/23.
 */
@Controller
@RequestMapping("/activity/")
public class ActivityController {

    @Autowired
    private IActivityService iActivityService;

    @Autowired
    private IFileService iFileService;

    //发布活动
    @RequestMapping("release.do")
    @ResponseBody
    public ServerResponse<String> release_activity(HttpSession session, Activity activity){
        User user = (User) session.getAttribute(Const.REGULAR_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        //获取用户id
        activity.setUserId(user.getId());
        //默认活动开始
        activity.setStatu(1);
        return iActivityService.release(activity);
    }

    //搜索活动（模糊查询）
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse search_activity(HttpSession session, @RequestParam(value = "activityName",defaultValue = "") String activityName, Integer activityId, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        User user = (User)session.getAttribute(Const.REGULAR_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iActivityService.search(activityName,activityId,pageNum,pageSize);
    }

    //删除活动
    @RequestMapping("delete.do")
    @ResponseBody
    public ServerResponse delete_activity(HttpSession session,int activityId){
        User user =(User)session.getAttribute(Const.REGULAR_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iActivityService.delete(activityId,user.getId());
    }


    //上传图片
    @RequestMapping("uploadActivityPicture.do")
    @ResponseBody
    public ServerResponse upload_activity_picture(HttpSession session, @RequestParam(value = "upload_activity_picture_file",required = false)MultipartFile file){//int activityId

        User user = (User)session.getAttribute(Const.REGULAR_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        String filePath = session.getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file,filePath);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
        Map map = Maps.newHashMap();
        map.put("uri",targetFileName);
        map.put("url",url);

        //将url上传至数据库保存
        //或者利用修改的接口进行修改
        //iActivityService.uploadActivityPictureUrl(url,activityId);
    return ServerResponse.createBySuccess(map);
    }

    //更新活动信息
    @RequestMapping(value = "update_information.do")
    @ResponseBody
    public  ServerResponse <ActivityVo>update_information(Activity activity, Integer activityId,HttpSession session){
        User user = (User) session.getAttribute(Const.REGULAR_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iActivityService.updateInformation(activity,activityId,user.getId());
    }

    //活动列表
    @RequestMapping("List.do")
    @ResponseBody
    public ServerResponse getList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,@RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        return iActivityService.getActivityList(pageNum,pageSize);
    }

//    //修改图片
//    @RequestMapping("upload.do")
//    @ResponseBody
//    public ServerResponse upload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
//        User user = (User)session.getAttribute(Const.REGULAR_USER);
//        if(user == null){
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录");
//        }
//        String path = request.getSession().getServletContext().getRealPath("upload");
//        String targetFileName = iFileService.upload(file,path);
//        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
//
//        Map fileMap = Maps.newHashMap();
//        fileMap.put("uri",targetFileName);
//        fileMap.put("url",url);
//
//        return ServerResponse.createBySuccess(fileMap);
//    }


}