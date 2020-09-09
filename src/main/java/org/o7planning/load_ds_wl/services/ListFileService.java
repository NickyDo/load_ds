package org.o7planning.load_ds_wl.services;

import java.util.List;
import org.o7planning.load_ds_wl.dao.ListFileDao;
import org.o7planning.load_ds_wl.entity.FileEntity;

import org.hibernate.Session;

public class ListFileService {
	private ListFileDao listFileDao;

	public ListFileService(Session session) {
		listFileDao = new ListFileDao(session);
	}

	public List<FileEntity> getAllData(String type) {
		return listFileDao.getAllData(type);
	}

	public String insertData(String name, String dir, String status, String dateUpload, String userUpload,
			String userApprove, String commentFile, String full, String mailTo) {
		return listFileDao.insertData(name, dir, status, dateUpload, userUpload, userApprove, commentFile, full,
				mailTo);
	}

	public String updateData(String name, String dir, String status, String dateUpload, String userUpload,
			String userApprove, String commentFile, String full, String mailTo) {
		return listFileDao.updateData(name, dir, status, dateUpload, userUpload, userApprove, commentFile, full,
				mailTo);
	}

	public String updateDataToChecker(String name, String status, String dateUpload) {
		return listFileDao.updateDataToChecker(name, status, dateUpload);
	}

	public String updateDeleteData(String name, String commentFile, String status, String userApprove) {
		return listFileDao.updateDeleteData(name, commentFile, status, userApprove);
	}

	public String deleteData(String id) {
		return listFileDao.deleteData(id);
	}
}
