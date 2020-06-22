package com.app.dashboard.user.dao;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface UserDao {
	public void insertAndUpdateUser(Map<String, Object> user) throws DataAccessException;
}
