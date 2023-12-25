package com.hahow.tung.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.hahow.tung.dao.UserDao;
import com.hahow.tung.dto.UserRegisterRequest;
import com.hahow.tung.model.User;
import com.hahow.tung.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Override
	public Integer register(UserRegisterRequest userRegisterRequest) {
		// 先檢查信箱是否有人註冊過
		User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

		if (user == null) {
			return userDao.createUser(userRegisterRequest);
		} else {
			log.warn("該 email 已被註冊過 => {}", userRegisterRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public User getUserById(Integer userId) {
		return userDao.getUserById(userId);
	}

}
