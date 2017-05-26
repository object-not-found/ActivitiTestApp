package com.app.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.demo.base.AbstractBaseService;
import com.app.demo.dao.UserMapper;
import com.app.demo.domain.User;
import com.app.demo.service.UserService;

@Service
public class UserServiceImpl extends AbstractBaseService<User, Long> implements UserService {

	@Autowired
	private UserMapper baseDao;
	
	@Override
	public void setBaseDao() {
		super.baseDao = baseDao;
	}

	@Override
	public List<User> list() {
		return baseDao.list();
	}

}
