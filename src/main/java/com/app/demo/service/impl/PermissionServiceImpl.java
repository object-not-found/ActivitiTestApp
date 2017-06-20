package com.app.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.demo.base.AbstractBaseService;
import com.app.demo.dao.PermissionMapper;
import com.app.demo.domain.Permission;
import com.app.demo.service.PermissionService;

@Service
public class PermissionServiceImpl extends AbstractBaseService<Permission, Long> implements PermissionService {

	@Autowired
	private PermissionMapper baseDao;
	
	@Override
	public void setBaseDao() {
		super.baseDao = baseDao;
	}

	@Override
	public List<Permission> findByPermissionIds(List<Integer> idList) {
		return baseDao.findByPermissionIds(idList);
	}

}
