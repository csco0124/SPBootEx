package com.app.dashboard.user.service;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.app.dashboard.user.dao.UserDao;


@Service
public class UserService {
	@Inject
	private UserDao userDao;
	
	public void insertAndUpdateUser(Map<String, Object> user) throws Exception{
		userDao.insertAndUpdateUser(user);
	}
	public Map<String, Object> getUserInfo(Map<String, Object> user) throws Exception{
		return userDao.getUserInfo(user);
	}
	
}
