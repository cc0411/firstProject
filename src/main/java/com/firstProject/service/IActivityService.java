package com.firstProject.service;

import com.firstProject.common.ServerResponse;
import com.firstProject.pojo.Activity;
import com.github.pagehelper.PageInfo;

/**
 * created by 吴家俊 on 2018/7/24.
 */
public interface IActivityService {

    //发布活动
    ServerResponse<String> release(Activity activity);
    //模糊查询
    ServerResponse<PageInfo> search(String activityName, Integer activityId,int pageNum, int pageSize);
    //删除活动
    ServerResponse<String> delete(int activityId,int userId);
    //更新url
    ServerResponse uploadActivityPictureUrl(String url,int activityId);

    ServerResponse updateInformation(Activity activity, int activityId, Integer userId);

    ServerResponse getActivityList(int pageNum,int pageSize);
}
