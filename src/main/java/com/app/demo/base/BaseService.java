package com.app.demo.base;

import java.io.Serializable;

public interface BaseService<T, ID extends Serializable> {
	T select(ID id);
	int insert(T t);
	int delete(ID id);
	int update(T t);
}
