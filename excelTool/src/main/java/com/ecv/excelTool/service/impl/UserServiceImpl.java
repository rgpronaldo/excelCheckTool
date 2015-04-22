package com.ecv.excelTool.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecv.excelTool.dao.UserDtoMapper;
import com.ecv.excelTool.dto.UserDto;
import com.ecv.excelTool.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private UserDtoMapper userDtoMapper;

	@Override
	public UserDto getUserById(int userId) {
		// TODO Auto-generated method stub
		return this.userDtoMapper.selectByPrimaryKey(userId);
	}

}
