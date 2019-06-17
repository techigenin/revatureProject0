package com.mJames.services;

import com.mJames.pojo.User;

public class UserServiceImplConsole implements UserService {
	@Override
	public boolean checkPassword(String pWord, User u) 
	{
			return u.checkPassword(pWord);	
	}
}
