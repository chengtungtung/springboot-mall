package com.hahow.tung.dao;

import com.hahow.tung.dto.UserRegisterRequest;
import com.hahow.tung.model.User;

public interface UserDao {

	Integer createUser(UserRegisterRequest userRegisterRequest);

	User getUserById(Integer userId);

	User getUserByEmail(String email);
}
