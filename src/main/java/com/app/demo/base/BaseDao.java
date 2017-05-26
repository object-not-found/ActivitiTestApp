package com.app.demo.base;

import java.io.Serializable;

public interface BaseDao<T, ID extends Serializable> {
	int insert(T t);
	int update(T t);
	int delete(ID id);
	T select(ID id);
}
