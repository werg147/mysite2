package com.javaex.dao;

import com.javaex.vo.UserVo;

public class UserDaoTest {

	public static void main(String[] args) {
		
		UserDao userDao = new UserDao();
		UserVo userVo = userDao.getUser("hs", "123");

		
		System.out.println(userVo);
		
		UserVo loginUser = new UserVo();
		loginUser = userDao.getUser(userVo.getId(),userVo.getPassword()); 
		
		System.out.println(loginUser);
		
	}

}
