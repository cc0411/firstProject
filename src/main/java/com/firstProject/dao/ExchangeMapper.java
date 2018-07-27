package com.firstProject.dao;

import com.firstProject.pojo.Exchange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExchangeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Exchange record);

    int insertSelective(Exchange record);

    Exchange selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Exchange record);

    int updateByPrimaryKey(Exchange record);


    List<Exchange> selectByNameAndStatus(@Param("goodName") String goodName, @Param("status") Integer status);

    int deleteByUserIdAndExchangeId(@Param("exchangeId") Integer exchangeId, @Param("userId") Integer userId);

    List<Exchange> selectExchangeList();

    int updateByUserIdExchangeId(@Param("exchangeId") Integer exchangeId, @Param("userId") Integer userId);

    int selectByUserId(Integer userId);


    List<Exchange> selectExchangeByUserId_U(@Param("userId") Integer userId);

    List<Exchange> selectExchangeByUserIdFinished_U(@Param("userId")Integer userId , @Param("i") int i);

    List<Exchange> selectExchangeByExchangeUserIdFinished_U(@Param("ExchangeuserId")Integer userId, @Param("i") int i);

}