package com.app.demo.service;

import java.util.List;

import com.app.demo.base.BaseService;
import com.app.demo.domain.Role;

public interface RoleService extends BaseService<Role, Long>{
	public List<Integer> findPermissionIds(Integer roleId);
	public List<Role> findByRoleIds(List<Integer> idList);
}
