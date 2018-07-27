package com.firstProject.service.Impl;

import com.firstProject.common.Const;
import com.firstProject.common.ResponseCode;
import com.firstProject.common.ServerResponse;
import com.firstProject.dao.ExchangeMapper;
import com.firstProject.dao.UserMapper;
import com.firstProject.pojo.Exchange;
import com.firstProject.pojo.User;
import com.firstProject.service.IExchangeService;
import com.firstProject.vo.ExchangeVo;
import com.firstProject.vo.ExchangeVo1;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Dorothy
 */
@Service("iExchangeService")
public class ExchangeServiceImpl implements IExchangeService {
    @Autowired
    private ExchangeMapper exchangeMapper;
    @Autowired
    private UserMapper userMapper;

    public ServerResponse<PageInfo> selectLikeByName(String name,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(name)){
            name=new StringBuilder().append("%").append(name).append("%").toString();
        }
        List<Exchange> exchangeList = exchangeMapper.selectByNameAndStatus(name,2);
        List<ExchangeVo1> exchangeVo1List = Lists.newArrayList();
        for(Exchange exchangeItem : exchangeList){
            ExchangeVo1 exchangeVo1 = assembleExchangeVoList(exchangeItem);
            exchangeVo1List.add(exchangeVo1);
        }
        PageInfo pageResult = new PageInfo(exchangeList);
        pageResult.setList(exchangeVo1List);
        return ServerResponse.createBySuccess(pageResult);
    }


    private ExchangeVo1 assembleExchangeVoList(Exchange exchangeItem){
    ExchangeVo1 exchangeVo1=new ExchangeVo1();
    exchangeVo1.setAddress(exchangeItem.getAddress());
    exchangeVo1.setName(exchangeItem.getName());
    exchangeVo1.setPrice(exchangeItem.getPrice());
    exchangeVo1.setTime(exchangeItem.getTime());
    exchangeVo1.setImageUrl(exchangeItem.getImageurl());
    exchangeVo1.setPhone(userMapper.exchangeSelectPhoneByUserId(exchangeItem.getUserId()));
    exchangeVo1.setUserId(exchangeItem.getUserId());
    return exchangeVo1;
    }

   public ServerResponse<String> uploadExchange(Exchange exchange,Integer userId){
       exchange.setStatu(2);
       exchange.setExchangeUserId(null);
       exchange.setUserId(userId);
        if(exchange!=null && userId == exchange.getUserId()){
            int rowCount=exchangeMapper.insertSelective(exchange);
            if(rowCount>0){
                return ServerResponse.createBySuccess(exchange.getImageurl(),"物物交换信息上传成功");
            }
            return ServerResponse.createByErrorMessage("物物交换信息上传失败");
        }
       return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
   }


   public ServerResponse<String> deleteExchange(Integer userId,Integer exchangeId){
        if(userId!=null && exchangeId!=null){
            int rowCount=exchangeMapper.deleteByUserIdAndExchangeId(exchangeId,userId);
            if(rowCount>0){
                return ServerResponse.createByErrorMessage("用户的物物交换信息成功删除");
            }
            return ServerResponse.createByErrorMessage("用户物物交换信息删除失败，请重试");
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
   }

    public ServerResponse getExchangeList(int pageNum ,int pageSize){
        PageHelper.startPage(pageNum,pageSize);

        List<Exchange> exchangeList = exchangeMapper.selectExchangeList();

        List<ExchangeVo> exchangeVoList = Lists.newArrayList();

        for (Exchange exchange:exchangeList){
            ExchangeVo exchangeVo = this.assembleExchangeVo(exchange);
            exchangeVoList.add(exchangeVo);
        }
        PageInfo pageResult = new PageInfo(exchangeList);
        pageResult.setList(exchangeVoList);
        return ServerResponse.createBySuccess(pageResult);
    }



    private ExchangeVo assembleExchangeVo(Exchange exchange){
        ExchangeVo exchangeVo = new ExchangeVo();
        exchangeVo.setName(exchange.getName());
        exchangeVo.setTime(exchange.getTime());
        exchangeVo.setStatus(exchange.getStatu());
        exchangeVo.setExpect(exchange.getExpect());
        exchangeVo.setImageUrl(exchange.getImageurl());

        User user = userMapper.selectByPrimaryKey(exchange.getUserId());
        exchangeVo.setPhone(user.getPhone());
        if (exchange.getStatu() == Const.ExchangeStatusEnum.EXCHANGED.getCode()){
            //如果交换成功则将expect设置为交换了的物品
            exchangeVo.setExpect(exchange.getExpect());
        }else{
            exchangeVo.setExpect(null);
        }
        return exchangeVo;
    }

    @Override
    public ServerResponse confirmUpdateStatus(Integer exchangeId,Integer userId){
        //this.deleteExchange(userId,exchange.getId());
        Exchange exchange = exchangeMapper.selectByPrimaryKey(exchangeId);
        if (exchange.getUserId()!=userId){
            return ServerResponse.createByErrorMessage("用户无权修改交换信息");
        }
        int rowCount = exchangeMapper.updateByUserIdExchangeId(exchangeId,userId);
        if (rowCount > 0){
            return ServerResponse.createBySuccess("交换已完成！",exchange);
        }
        return ServerResponse.createByErrorMessage("交换物品失败");
    }

    public ServerResponse updateInfo(Integer userId,Exchange exchange){
        if (exchange != null){
            int rowCount1 = exchangeMapper.updateByPrimaryKeySelective(exchange);
            int rowCount2 = exchangeMapper.selectByUserId(userId);
            if (rowCount1 > 0 && rowCount2 > 0){
                return ServerResponse.createBySuccess("更新信息成功",exchange);
            }
            return ServerResponse.createByErrorMessage("更新信息失败");
        }
        return ServerResponse.createByErrorMessage("信息有误，交换不成功");
    }



}