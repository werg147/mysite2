package com.javaex.dao;

import com.javaex.vo.UserVo;

public class UserDaoTest {

	public static void main(String[] args) {
		
		UserDao userDao = new UserDao();
		UserVo userVo = userDao.getUser("hs", "123");

		
		System.out.println(userVo);
	}

}
