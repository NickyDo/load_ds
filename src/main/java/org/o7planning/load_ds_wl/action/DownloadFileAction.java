package org.o7planning.load_ds_wl.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DownloadFileAction extends ActionSupport {
	private InputStream fileInputStream;
	private String fileName;

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public String execute() throws FileNotFoundException {
		if (!ActionContext.getContext().getSession().containsKey("userName")) {
			return "error";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		setFilename(request.getParameter("idName"));
		System.out.print("idName" + getFilename());

		// Cookie cookie = new Cookie("download",
		// request.getParameter("idName"));
		try {
			String pathFile = "/app/setup/tonbeller/sironKYC/client/0001/data/input/" + request.getParameter("idName");
			File file = new File(pathFile);

			if (file.isFile()) {
				fileInputStream = new FileInputStream(file);
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return SUCCESS;
	}

	public String downloadErrorFile() throws FileNotFoundException {
		if (!ActionContext.getContext().getSession().containsKey("userName")) {
			return "error";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String tempFile = request.getParameter("idName");
		setFilename(tempFile.substring(0, tempFile.lastIndexOf('.')) + ".csv");
		System.out.print("idName" + getFilename());

		// Cookie cookie = new Cookie("download",
		// request.getParameter("idName"));
		try {
			String pathFile = "/app/setup/tonbeller/sironKYC/client/0001/data/input/error_file/" + fileName;
			File file = new File(pathFile);

			if (file.isFile()) {
				fileInputStream = new FileInputStream(file);
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return SUCCESS;
	}

	private String getFilename() {
		return this.fileName;
	}

	public void setFilename(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

}
