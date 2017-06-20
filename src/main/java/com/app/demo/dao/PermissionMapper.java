package com.app.demo.dao;

import java.util.List;

import com.app.demo.base.BaseDao;
import com.app.demo.domain.Permission;

public interface PermissionMapper extends BaseDao<Permission, Long>{
	List<Permission> findByPermissionIds(List<Integer> idList);
}
