package com.firstProject.dao;

import com.firstProject.pojo.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);


    //通过名字进行模糊查询wjj
    List<Activity> selectByNameAndActivityId(@Param("activityName") String activityName,@Param("activityId")Integer activityId);
    //通过userId和activityId查找记录wjj
    int selectByActivityIdUserId( @Param("userId") int userId,@Param("activityId") int activityId);
    //yyj
    Activity selectActivityByActivityIdUserId(@Param("activityId") int activityId, @Param("userId") int userId);
    //yyj
    List<Activity> selectList();
    //yyj
    Activity selectActivityId(Integer id);
    //yyj
    int selectActivity(Integer activityId);


    List<Activity> selectByUserIdU(Integer userId);
}