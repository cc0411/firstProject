package com.firstProject.service;

import com.firstProject.common.ServerResponse;
import com.firstProject.pojo.Exchange;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by Dorothy
 */
public interface IExchangeService {
    ServerResponse<PageInfo> selectLikeByName(String name, int pageNum, int pageSize);

    ServerResponse<String> uploadExchange(Exchange exchange, Integer userId);

    ServerResponse<String> deleteExchange(Integer userId, Integer exchangeId);

    ServerResponse getExchangeList(int pageNum, int pageSize);

    ServerResponse<String> confirmUpdateStatus(Integer exchangeId, Integer userId);

    ServerResponse updateInfo(Integer userId, Exchange exchange);



}