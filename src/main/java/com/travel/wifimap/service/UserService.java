package com.travel.wifimap.service;

import java.util.List;

import com.travel.wifimap.domain.User;

public interface UserService {

	public void saveUser(User user);

	public List<User> findUsers();

	public User findUserById(String id);

	public User findUserByEmail(String email);

	public void removeUser(String id);

	public User getUserByOAuthToken(String token);
}