package com.travel.wifimap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.wifimap.dao.UserDao;
import com.travel.wifimap.domain.User;
import com.travel.wifimap.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public void saveUser(User user) {
		userDao.saveOrUpdate(user);
	}

	@Override
	@Transactional
	public List<User> findUsers() {
		return userDao.list();
	}

	@Override
	@Transactional
	public User findUserById(String id) {
		return (User) userDao.findById(id);
	}

	@Override
	@Transactional
	public User findUserByEmail(String email) {
		return userDao.findUserByEmail(email);
	}

	@Override
	@Transactional
	public void removeUser(String id) {
		User user = (User) userDao.findById(id);
		if (user != null) {
			userDao.delete(user);
		}
	}

	@Override
	@Transactional
	public User getUserByOAuthToken(String token) {
		return userDao.getUserByOAuthToken(token);
	}

}
