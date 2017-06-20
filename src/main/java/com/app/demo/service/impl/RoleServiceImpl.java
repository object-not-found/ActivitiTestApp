package com.app.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.demo.base.AbstractBaseService;
import com.app.demo.dao.RoleMapper;
import com.app.demo.domain.Role;
import com.app.demo.service.RoleService;

@Service
public class RoleServiceImpl extends AbstractBaseService<Role, Long> implements RoleService {

	@Autowired
	private RoleMapper baseDao;
	
	@Override
	public void setBaseDao() {
		super.baseDao = baseDao;
	}

	@Override
	public List<Integer> findPermissionIds(Integer roleId) {
		return baseDao.findPermissionIds(roleId);
	}

	@Override
	public List<Role> findByRoleIds(List<Integer> idList) {
		return baseDao.findByRoleIds(idList);
	}

}
