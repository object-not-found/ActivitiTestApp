package com.app.demo.domain;

import java.io.Serializable;

public class Permission implements Serializable {

	private static final long serialVersionUID = -28394239565518977L;
	
	private Integer id;
	private String permissionName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
}
