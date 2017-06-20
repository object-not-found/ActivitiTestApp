package com.app.demo.dao;

import java.util.List;

import com.app.demo.base.BaseDao;
import com.app.demo.domain.Role;

public interface RoleMapper extends BaseDao<Role, Long>{
	List<Integer> findPermissionIds(Integer roleId);
	List<Role> findByRoleIds(List<Integer> idList);
}
