package com.mJames.services;

import com.mJames.pojo.User;

public interface UserService {

	boolean checkPassword(String pWord, User u);

}