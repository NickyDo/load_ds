package org.o7planning.load_ds_wl.action;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.o7planning.load_ds_wl.services.ListFileService;
import org.o7planning.load_ds_wl.util.HibernateUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteFileAction extends ActionSupport {
	public String to;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String execute() throws FileNotFoundException {
		if (!ActionContext.getContext().getSession().containsKey("userName")) {
			return "error";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		System.out.print("delete idName:" + request.getParameter("idName"));
		Session session = HibernateUtil.getSessionFactory().openSession();

		String pathFile = "/app/setup/tonbeller/sironKYC/client/0001/data/input/" + request.getParameter("idName");
		ListFileService listFileService = new ListFileService(session);
		if (request.getParameter("fileType") != null) {
			setTo(request.getParameter("fileType"));
		}
		try {
			Files.deleteIfExists(Paths.get(pathFile));
			String id = request.getParameter("id");
			if (id != null) {
				// listFileService.deleteData(request.getParameter("id"));
				listFileService.updateDeleteData(request.getParameter("idName"), request.getParameter("inputData"),
						"deleted", " ");
			}

		} catch (NoSuchFileException e) {
			System.out.println("No such file/directory exists");
			return "error";
		} catch (DirectoryNotEmptyException e) {
			System.out.println("Directory is not empty.");
			return "error";
		} catch (IOException e) {
			System.out.println("Invalid permissions.");
			return "error";
		}

		System.out.println("Deletion successful.");

		return SUCCESS;
	}

}
