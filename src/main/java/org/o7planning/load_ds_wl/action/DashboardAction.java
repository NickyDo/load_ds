package org.o7planning.load_ds_wl.action;

import java.util.List;

import org.hibernate.Session;
import org.o7planning.load_ds_wl.entity.UserEntity;
import org.o7planning.load_ds_wl.services.UserService;
import org.o7planning.load_ds_wl.util.HibernateUtil;

import com.opensymphony.xwork2.ActionContext;

public class DashboardAction {
	private List<UserEntity> userData;

	public String dashboardDetail() {
		if (!ActionContext.getContext().getSession().containsKey("userName")) {
			return "logout";
		}
		Session session = HibernateUtil.getSessionFactory().openSession();

		UserService userService = new UserService(session);
		userData = userService.getChecker();

		session.close();
		return "success";
	}

	public String logout() {
		if (!ActionContext.getContext().getSession().containsKey("userName")
				|| !ActionContext.getContext().getSession().containsKey("password")) {
			return "logout";
		}
		ActionContext.getContext().getSession().clear();
		return "logout";
	}

	public List<UserEntity> getUserData() {
		return userData;
	}

	public void setUserData(List<UserEntity> userData) {
		this.userData = userData;
	}

	
}
