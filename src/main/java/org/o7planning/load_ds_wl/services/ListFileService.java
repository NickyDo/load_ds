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

	public List<FileEntity> getAllData() {
		return listFileDao.getAllData();
	}

	public String insertData(String name, String dir, String typefile, String deletetype, String size,
			String thumbnailurl, String deleteurl, String full) {
		return listFileDao.insertData(name, dir, typefile, deletetype, size, thumbnailurl, deleteurl, full);
	}
}
