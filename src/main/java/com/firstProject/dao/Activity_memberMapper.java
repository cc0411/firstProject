package com.firstProject.dao;

import com.firstProject.pojo.Activity_memberKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Activity_memberMapper {
    int deleteByPrimaryKey(Activity_memberKey key);

    int insert(Activity_memberKey record);

    int insertSelective(Activity_memberKey record);



    //搜索活动参加人数wjj
    int selectByActivityId(int activityId);
    //查找参加活动的人的idwjj
    List<Integer> selectUserIdByActivityId(int activityId);
    //yyj
    Activity_memberKey selectByActivityIdUserId(@Param("activityId") int activityId, @Param("userId") int userId);


    List<Activity_memberKey> selectByUserIdU(Integer userId);//查somebody参加的活动

    int selectByActivityIdU(Integer activityId);//查参与人数

    List<Activity_memberKey> selectPepleByActivityIdU(Integer activityId);//差某个活动有哪些参与人
}