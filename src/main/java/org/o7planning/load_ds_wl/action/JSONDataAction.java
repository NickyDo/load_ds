package org.o7planning.load_ds_wl.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.o7planning.load_ds_wl.dao.ListFileDao;
import org.o7planning.load_ds_wl.entity.FileEntity;
import org.o7planning.load_ds_wl.services.ListFileService;
import org.o7planning.load_ds_wl.util.HibernateUtil;
import org.hibernate.SessionFactory;

import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class JSONDataAction extends ActionSupport {
	private File[] files;
	private File[] fileUpload;
	private String[] filesFileName;
	private String[] filesContentType;
	private String to;
	private List<FileEntity> fileDataList;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public List<FileEntity> getFileDataList() {
		return fileDataList;
	}

	public void setFileDataList(List<FileEntity> fileDataList) {
		this.fileDataList = fileDataList;
	}

	/**
	 * This is the path to save uploaded file, which is configured in struts.xml
	 */
	private String saveDirectory;

	public String sendToChecker() {
		String fileName = "";
		Session session = HibernateUtil.getSessionFactory().openSession();

		HttpServletRequest request = ServletActionContext.getRequest();
		System.out.println("startsendToChecker" + request.getParameter("idName"));

		if (request.getParameter("idName") != null) {
			fileName = request.getParameter("idName");
		}

		System.out.println("fileName" + fileName);

		ListFileService listFileService = new ListFileService(session);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String dateUpload = dtf.format(now);
		if (!fileName.isEmpty()) {
			System.out.println("sendToChecker update");
			listFileService.updateDataToChecker(fileName, "pending", dateUpload);

		}
		session.close();

		return "success";
	}

	public String execute() throws Exception {
		System.out.println("runcopy file");
		Session session = HibernateUtil.getSessionFactory().openSession();
		System.out.println("session file");

		ListFileService listFileService = new ListFileService(session);
		System.out.println("service file" + to);
		HttpServletRequest request = ServletActionContext.getRequest();
		System.out.println("selected" + request.getParameter("selected"));
		String selected = " ";
		if (request.getParameter("selected") != null) {
			selected = request.getParameter("selected");
		}

		for (int i = 0; i < files.length; i++) {
			File uploadedFile = files[i];
			System.out.println(" file" + files[i]);
			System.out.println(" filess" + files);

			String fileName = filesFileName[i];
			File destFile = new File(saveDirectory + File.separator + fileName);
			fileDataList = listFileService.getAllData("all");
			boolean isUpdate = false;
			if (!fileDataList.isEmpty()) {
				for (int i1 = 0; i1 < fileDataList.size(); i1++) {
					if (fileDataList.get(i1).getName().equals(fileName)) {
						isUpdate = true;
					}
				}
			}

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			String dateUpload = dtf.format(now);
			System.out.println(dtf.format(now));
			String userName = (String) ActionContext.getContext().getSession().get("userName");
			String mail = userName + "@vibtest.dev";

			try {
				FileUtils.copyFile(uploadedFile, destFile);
			} catch (IOException ex) {
				System.out.println("Could not copy file " + fileName);
				ex.printStackTrace();
			}
			System.out.print("p1" + userName);

			String excelPath = saveDirectory + '/' + fileName;
			System.out.println("p1" + excelPath);

			String workbook = configValidateFile(fileName, saveDirectory + "/error_file/", "failed", dateUpload,
					userName, " ", " ", selected, mail, isUpdate);
			// test
//			workbook = "success";
			//
			System.out.println("workbook" + workbook);
			if (workbook.equals("error")) {
				// delete file
				try {
					System.out.println("aaa" + Paths.get(saveDirectory + '/' + fileName));
					Files.deleteIfExists(Paths.get(saveDirectory + '/' + fileName));
					return "error";
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
			}

			System.out.println("runcopy file" + uploadedFile);
			if (isUpdate) {
				listFileService.updateData(fileName, saveDirectory, "pre-pending", dateUpload, userName, " ", " ",
						selected, mail);
			} else {
				listFileService.insertData(fileName, saveDirectory, "pre-pending", dateUpload, userName, " ", " ",
						selected, mail);
			}
		}
		session.close();

		return Action.SUCCESS;
	}

	public String configValidateFile(String fileName, String dir, String status, String dateUpload, String userUpload,
			String userApprove, String commentFile, String full, String mailTo, boolean isUpdate) {

		System.out.println("FileName" + fileName);
		System.out.println("dir" + dir);

		try {
			System.out.println("error file" + Paths.get("/app/setup/tonbeller/sironKYC/client/0001/data/input/error_file"
					+ '/' + fileName.substring(0, fileName.lastIndexOf('.')) + ".xlsx"));
			Files.deleteIfExists(Paths.get("/app/setup/tonbeller/sironKYC/client/0001/data/input/error_file" + '/'
					+ fileName.substring(0, fileName.lastIndexOf('.')) + ".xlsx"));

		} catch (NoSuchFileException e) {
			System.out.println("No such file/directory exists");
		} catch (DirectoryNotEmptyException e) {
			System.out.println("Directory is not empty.");
		} catch (IOException e) {
			System.out.println("Invalid permissions.");
		}

		String scriptContent = "#!/bin/bash \nexcelPath=/app/setup/tonbeller/sironKYC/client/0001/data/input/" + fileName
				+ "\nerrorEntries=/app/setup/tonbeller/sironKYC/client/0001/data/input/error_file/"
				+ fileName.substring(0, fileName.lastIndexOf('.')) + ".xlsx";
		try {
			Writer output = new BufferedWriter(new FileWriter(
					"/app/setup/tonbeller/sironKYC/client/0001/data/input/VIB/VIB_Validate/vib/vib_validate_0_1/contexts/Default.properties"));
			output.write(scriptContent);
			output.close();
			String validate = runValidate(fileName);
			System.out.println("session validate" + validate);

			if (validate.equals("success")) {
				File tmpDir = new File("/app/setup/tonbeller/sironKYC/client/0001/data/input/error_file/"
						+ fileName.substring(0, fileName.lastIndexOf('.')) + ".xlsx");
				boolean exists = tmpDir.exists();

				System.out.println("exists" + exists);

				if (exists) {
					Session session = HibernateUtil.getSessionFactory().openSession();
					System.out.println("session file");

					ListFileService listFileService = new ListFileService(session);
					if (isUpdate) {
						listFileService.updateData(fileName, dir, status, dateUpload, userUpload, " ", " ", full,
								mailTo);
					} else {
						listFileService.insertData(fileName, dir, status, dateUpload, userUpload, " ", " ", full,
								mailTo);
					}

					return "error";
				}
				return "success";
			} else {
				return "error";
			}

			// Runtime.getRuntime().exec("chmod u+x
			// D:/Test/Default.properties");
		} catch (IOException ex) {
			ex.printStackTrace();
			return "error";
		}

	}

	public String runValidate(String fileName) {
		ProcessBuilder processBuilder = new ProcessBuilder();
		// ProcessBuilder pb = new ProcessBuilder("src/lexparser.sh",
		// "myArg1",
		// "myArg2");

		// linux
		// processBuilder.command("/app/setup/tonbeller/sironKYC/client/0001/data/input/Covert_BIDV/Covert_BIDV_run.sh");
		// -- Windows --

		// Run a command
		processBuilder.command(
				"/app/setup/tonbeller/sironKYC/client/0001/data/input/VIB/VIB_Validate/VIB_Validate_run.bat");

		// Run a bat file
		// processBuilder.command("C:\\Users\\mkyong\\hello.bat");
		try {

			Process process = processBuilder.start();

			StringBuilder output = new StringBuilder();
			System.out.println("start validate");

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			System.out.println("validating");


			int exitVal = process.waitFor();
			if (exitVal == 0) {
				System.out.println("Success2!");
				System.out.println(output);
				return "success";
			} else {
				// abnormal...
				System.out.println("error validate" + exitVal);
				return "error";
			}

		} catch (IOException e) {
			System.out.println("IOException" + e);

			e.printStackTrace();
			return "error";
		} catch (InterruptedException e) {
			System.out.println("InterruptedException" + e);

			e.printStackTrace();
			return "error";
		}
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
