package com.app.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.demo.base.AbstractBaseService;
import com.app.demo.dao.UserMapper;
import com.app.demo.dataSource.TargetDataSource;
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

	@Cacheable(value="userList")
//	@TargetDataSource("db1")  //指定数据源
	@Override
	public List<User> list() {
		System.out.println("==========UserServiceImpl.list()  从数据库中获取数据============");
		return baseDao.list();
	}

}
