package com.app.demo.service;

import java.util.List;

import com.app.demo.base.BaseService;
import com.app.demo.domain.Permission;

public interface PermissionService extends BaseService<Permission, Long>{
	public List<Permission> findByPermissionIds(List<Integer> idList);
}
