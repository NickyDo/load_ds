package org.o7planning.load_ds_wl.action;

import java.util.List;

import org.hibernate.Session;
import org.o7planning.load_ds_wl.entity.FileEntity;
import org.o7planning.load_ds_wl.services.ListFileService;
import org.o7planning.load_ds_wl.util.HibernateUtil;

import com.opensymphony.xwork2.ActionSupport;

public class FileDataAction  extends ActionSupport{
	private List<FileEntity> fileDataList;

	public List<FileEntity> getFileDataList() {
		return fileDataList;
	}

	public void setFileDataList(List<FileEntity> fileDataList) {
		this.fileDataList = fileDataList;
	}
	public String search() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		System.out.println("session file");

		ListFileService listFileService = new ListFileService(session);
		fileDataList = listFileService.getAllData();
		session.close();
		return "success";
	}
	
//	public String delete() {
//        // delete student has delFlag = true
//        int size = fileDataList.size();
//        if (fileDataList != null && size > 0) {
//            for (int i = 0 ; i < size; i++) {
//                if (fileDataList.get(i) != null
//                        && fileDataList.get(i).isDelFlag()) {
//                	fileDataList.remove(i);
//                    size = fileDataList.size();
//                }
//            }
//        }
//        return SUCCESS;
//    }
}
