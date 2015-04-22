package com.ecv.excelTool.dao;

import com.ecv.excelTool.dto.UserDto;


public interface UserDtoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserDto record);

    int insertSelective(UserDto record);

    UserDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserDto record);

    int updateByPrimaryKey(UserDto record);
}