package org.o7planning.load_ds_wl.services;

import java.util.List;

import org.hibernate.Session;

import org.o7planning.load_ds_wl.dao.UserDao;
import org.o7planning.load_ds_wl.entity.UserEntity;

public class UserService {

	private UserDao userDao;
	
	public UserService(Session session){
		userDao = new UserDao(session);
	}
	
	public List<UserEntity> getAllType(){
		return userDao.getAllData();
	}
	
	public List<UserEntity> getChecker(){
		return userDao.getChecker();
	}
}
