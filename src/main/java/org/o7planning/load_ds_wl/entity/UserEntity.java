package org.o7planning.load_ds_wl.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


public class UserEntity implements Serializable{


	public UserEntity() {
		
	}

	private String userName;
	private String password;
	private String typeUser;

	public String getTypeUser() {
		return typeUser;
	}

	public void setTypeUser(String typeUser) {
		this.typeUser = typeUser;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
