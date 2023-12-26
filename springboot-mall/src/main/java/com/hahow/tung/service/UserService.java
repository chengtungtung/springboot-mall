package com.hahow.tung.service;

import com.hahow.tung.dto.UserLoginRequest;
import com.hahow.tung.dto.UserRegisterRequest;
import com.hahow.tung.model.User;

public interface UserService {

	Integer register(UserRegisterRequest userRegisterRequest);
	
	User getUserById(Integer userId);
	
	User login(UserLoginRequest userLoginRequest);
	
	
}
