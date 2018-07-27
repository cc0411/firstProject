package com.firstProject.controller;

import com.firstProject.common.Const;
import com.firstProject.common.ResponseCode;
import com.firstProject.common.ServerResponse;
import com.firstProject.pojo.Exchange;
import com.firstProject.pojo.User;
import com.firstProject.service.IExchangeService;
import com.firstProject.service.IFileService;
import com.firstProject.service.IUserService;
import com.firstProject.utill.PropertiesUtil;
import com.firstProject.vo.ExchangeVo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.trim;

/**
 * Created by Dorothy
 */
@Controller
@RequestMapping("/exchange/")
public class ExchangeController {
    @Autowired
    private IExchangeService iExchangeService;
    @Autowired
    private IFileService iFileService;
    @Autowired
    private IUserService iUserService;


    @RequestMapping("selectLike.do")
    @ResponseBody
    public ServerResponse selectLikeByName(HttpSession session, String name,
                                           @RequestParam(value="pageNum",defaultValue = "1") int pageNum,
                                           @RequestParam(value="pageSize",defaultValue = "10") int pageSize){
         User user=(User)session.getAttribute(Const.REGULAR_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iExchangeService.selectLikeByName(name.trim(),pageNum,pageSize);
    }


    @RequestMapping("upload_exchange.do")
    @ResponseBody
    public ServerResponse uploadExchange(HttpSession session, Exchange exchange){
       User user=(User)session.getAttribute(Const.REGULAR_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iExchangeService.uploadExchange(exchange,user.getId());
    }

    @RequestMapping("delete_exchange.do")
    @ResponseBody
    public ServerResponse<String> deleteExchange(HttpSession session,Integer exchangeId){
       User user=(User)session.getAttribute(Const.REGULAR_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());

        }
        Integer userId=user.getId();
        return iExchangeService.deleteExchange(userId,exchangeId);
    }

    //修改交换信息
    @RequestMapping("modify_info.do")
    @ResponseBody
    public ServerResponse modifyInfo (HttpSession session, Exchange exchange){
        User user = (User)session.getAttribute(Const.REGULAR_USER);
        if (user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iExchangeService.updateInfo(user.getId(), exchange);
    }


    //物物交换列表
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<ExchangeVo> list (HttpSession session,
                                            @RequestParam(value = "pageNum",defaultValue = "1")  int pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User)session.getAttribute(Const.REGULAR_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iExchangeService.getExchangeList(pageNum,pageSize);
    }

    @RequestMapping("confirm_status.do")
    @ResponseBody
    public ServerResponse<String> confirmStatus (HttpSession session,Integer exchangeId){
        User user = (User)session.getAttribute(Const.REGULAR_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iExchangeService.confirmUpdateStatus(exchangeId,user.getId());
    }


    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        User user = (User)session.getAttribute(Const.REGULAR_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;

            Map fileMap = Maps.newHashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url",url);
            return ServerResponse.createBySuccess(fileMap);
    }

}