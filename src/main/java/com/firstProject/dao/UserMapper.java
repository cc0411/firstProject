package com.firstProject.dao;

import com.firstProject.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


    int checkUsername(String userName);

    User selectLogin(@Param("userName") String userName, @Param("password") String password);

    User selectUserId(Integer userId);

    User selectList(Integer userId);

    String exchangeSelectPhoneByUserId(Integer userId);

    int checkPassword_U(@Param(value ="password")String password,@Param(value = "name") String name );
}