package com.app.demo.dao;

import java.util.List;

import com.app.demo.base.BaseDao;
import com.app.demo.domain.User;

public interface UserMapper extends BaseDao<User, Long>{
	List<User> list();
	List<Integer> findRoleIds(Integer userId);
}
