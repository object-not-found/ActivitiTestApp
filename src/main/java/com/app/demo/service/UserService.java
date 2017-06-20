package com.app.demo.service;

import java.util.List;

import com.app.demo.base.BaseService;
import com.app.demo.domain.User;

public interface UserService extends BaseService<User, Long>{
	public List<User> list();
	public List<Integer> findRoleIds(Integer userId);
}
