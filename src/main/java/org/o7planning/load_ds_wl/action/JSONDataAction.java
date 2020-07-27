package org.o7planning.load_ds_wl.action;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.o7planning.load_ds_wl.dao.ListFileDao;
import org.o7planning.load_ds_wl.entity.FileEntity;
import org.o7planning.load_ds_wl.services.ListFileService;
import org.o7planning.load_ds_wl.util.HibernateUtil;
import org.hibernate.SessionFactory;

import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

public class JSONDataAction extends ActionSupport {
	private File[] files;
	private File[] fileUpload;
	private String[] filesFileName;
	private String[] filesContentType;
	private String to;

	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * This is the path to save uploaded file, which is configured in struts.xml
	 */
	private String saveDirectory;
	public String execute() throws Exception {
		System.out.println("runcopy file" );
		Session session = HibernateUtil.getSessionFactory().openSession();
		System.out.println("session file");

		ListFileService listFileService = new ListFileService(session);
		System.out.println("service file" + to);

//		return listFileDao.insertData(name, dir, typefile, deletetype, size, thumbnailurl, deleteurl, full);

		// copy the uploaded files into pre-configured location
		for (int i = 0; i < files.length; i++) {
			File uploadedFile = files[i];
			System.out.println(" file" + files[i]);
			System.out.println(" filess" + files);

			String fileName = filesFileName[i];
			File destFile = new File(saveDirectory + File.separator + fileName);
			listFileService.insertData(fileName, saveDirectory, "DELETE", "100", "100", "100", "100","1");
			try {
				FileUtils.copyFile(uploadedFile, destFile);
			} catch (IOException ex) {
				System.out.println("Could not copy file " + fileName);
				ex.printStackTrace();
			}
			System.out.println("runcopy file" + uploadedFile);

		}
		session.close();

		return Action.SUCCESS;
	}
	public void setFilesContentType(String[] fileUploadContentTypes) {
		this.filesContentType = fileUploadContentTypes;
	}

	public String getSaveDirectory() {
		return saveDirectory;
	}

	public void setSaveDirectory(String saveDir) {
		this.saveDirectory = saveDir;
	}

	

	public void setFileUpload(File[] fileUpload) {
		this.fileUpload = fileUpload;
	}

	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}

	public String[] getFilesFileName() {
		return filesFileName;
	}

	public void setFilesFileName(String[] fileUploadFileNames) {
		this.filesFileName = fileUploadFileNames;
	}

	public String[] getFilesContentType() {
		return filesContentType;
	}

}
