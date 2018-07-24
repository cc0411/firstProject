package com.firstProject.dao;

import com.firstProject.pojo.Activity_memberKey;

public interface Activity_memberMapper {
    int deleteByPrimaryKey(Activity_memberKey key);

    int insert(Activity_memberKey record);

    int insertSelective(Activity_memberKey record);
}