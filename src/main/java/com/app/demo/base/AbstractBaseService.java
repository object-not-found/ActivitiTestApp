package com.app.demo.base;

import java.io.Serializable;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

public abstract class AbstractBaseService<T, ID extends Serializable> implements BaseService<T, ID> {
	protected BaseDao<T, ID> baseDao;
	
	public abstract void setBaseDao();

	@Cacheable("select")
	@Override
	public T select(Map<String, Object> map) {
		this.setBaseDao();
		System.out.println("========select()===========");
		return baseDao.select(map);
	}

	@Override
	public int insert(T t) {
		this.setBaseDao();
		return baseDao.insert(t);
	}

	@Override
	public int delete(ID id) {
		this.setBaseDao();
		return baseDao.delete(id);
	}

	@Override
	public int update(T t) {
		this.setBaseDao();
		return baseDao.update(t);
	}
	
	
}
