package com.travel.wifimap.dao;

import com.travel.wifimap.domain.User;

public interface UserDao extends CRUD<User> {

	public User findUserByEmail(String email);

	public User getUserByOAuthToken(String token);
}
