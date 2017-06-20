package com.app.demo.domain;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {

	private static final long serialVersionUID = -7324310485275631317L;
	
	private Integer id;
	private String roleName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
