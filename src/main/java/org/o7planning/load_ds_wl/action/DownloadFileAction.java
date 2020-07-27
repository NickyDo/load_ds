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
	private String filename;
	private String idName;

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public String execute() throws FileNotFoundException {
		HttpServletRequest request = ServletActionContext.getRequest();
		setFilename(request.getParameter("idName"));
		System.out.print("idName" + request.getParameter("idName"));

		// Cookie cookie = new Cookie("download",
		// request.getParameter("idName"));
		try {
			String pathFile = "D:/Test/Upload/" + request.getParameter("idName");
			fileInputStream = new FileInputStream(new File(pathFile));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	private String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
