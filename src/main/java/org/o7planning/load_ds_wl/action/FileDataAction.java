package org.o7planning.load_ds_wl.action;

import java.util.List;

import org.hibernate.Session;
import org.o7planning.load_ds_wl.entity.FileEntity;
import org.o7planning.load_ds_wl.entity.UserEntity;
import org.o7planning.load_ds_wl.services.ListFileService;
import org.o7planning.load_ds_wl.services.UserService;
import org.o7planning.load_ds_wl.util.HibernateUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FileDataAction extends ActionSupport {
	private List<FileEntity> fileDataList;
	private List<FileEntity> fileDataListChecker;
	private List<UserEntity> userData;

	public List<FileEntity> getFileDataList() {
		return fileDataList;
	}

	public void setFileDataList(List<FileEntity> fileDataList) {
		this.fileDataList = fileDataList;
	}

	public List<FileEntity> getFileDataListChecker() {
		return fileDataListChecker;
	}

	public void setFileDataListChecker(List<FileEntity> fileDataListChecker) {
		this.fileDataListChecker = fileDataListChecker;
	}

	public List<UserEntity> getUserData() {
		return userData;
	}

	public void setUserData(List<UserEntity> userData) {
		this.userData = userData;
	}

	public String search() {
		if (!ActionContext.getContext().getSession().containsKey("userName")) {
			return "error";
		}
		Session session = HibernateUtil.getSessionFactory().openSession();
		System.out.println("session file 1");
		UserService userService = new UserService(session);
		userData = userService.getChecker();

		ListFileService listFileService = new ListFileService(session);
		fileDataList = listFileService.getAllData("all");
		session.close();
		return "success";
	}

	public String getDataChecker() {
		if (!ActionContext.getContext().getSession().containsKey("userName")) {
			return "error";
		}
		Session session = HibernateUtil.getSessionFactory().openSession();
		System.out.println("session file2");

		ListFileService listFileService = new ListFileService(session);
		fileDataListChecker = listFileService.getAllData("checker");
		session.close();
		return "success";
	}

}
