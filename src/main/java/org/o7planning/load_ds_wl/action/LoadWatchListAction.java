package org.o7planning.load_ds_wl.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.opencsv.CSVReader;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.o7planning.load_ds_wl.services.ListFileService;
import org.o7planning.load_ds_wl.util.HibernateUtil;

public class LoadWatchListAction extends ActionSupport {
	private static final long serialVersionUID = 7299264265184515893L;
	public String to;
	public String type;
	public File fileResult;
	public String typeResult;

	public String getTypeResult() {
		return typeResult;
	}

	public void setTypeResult(String typeResult) {
		this.typeResult = typeResult;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public File getFileResult() {
		return fileResult;
	}

	public void setFileResult(File fileResult) {
		this.fileResult = fileResult;
	}

	@Override
	public String execute() {
		if (!ActionContext.getContext().getSession().containsKey("userName")) {
			return "error";
		}
		return "success";
	}

	public File getLatestFilefromDir(String dirPath) throws IOException {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		String fileName = lastModifiedFile.getName();
		System.out.println("lastchange: " + fileName);

		int n_lines = 5;
		int counter = 0;

		String line = " ";
		@SuppressWarnings("resource")
		ReversedLinesFileReader object = new ReversedLinesFileReader(lastModifiedFile);
		while (counter < n_lines) {
			System.out.println(object.readLine());

			if (counter == 1) {
				line = line + object.readLine();
			}
			counter++;
		}
		System.out.println("line " + line);

		if (line.contains("Processing successful")) {
			System.out.println("Processing successful");
			setTypeResult("load_success");
		} else {
			System.out.println("Processing failed");
			setTypeResult("load_error");
		}

		return lastModifiedFile;
	}

	public String executeShFile() throws IOException {

		HttpServletRequest request = ServletActionContext.getRequest();

		String outputcsv = request.getParameter("idName");
		String type = request.getParameter("idType");
		if (request.getParameter("fileType") != null) {
			setTo(request.getParameter("fileType"));
			setType("success");
		}
		System.out.println("FileName" + outputcsv + type);

	
		String scriptContent = "#!/bin/bash \nexcelPath=/app/setup/tonbeller/sironKYC/client/0001/data/input/"
				+ outputcsv + "\noutputCSV=/app/setup/tonbeller/sironKYC/client/0001/data/input/output.csv";
		try {
			Writer output = new BufferedWriter(new FileWriter(
					"/app/setup/tonbeller/sironKYC/client/0001/data/input/VIB/VIB_Convert/newpro/vib_convert_0_1/contexts/Default.properties"));
			output.write(scriptContent);
			output.close();
			runSh(type, outputcsv);

			// Runtime.getRuntime().exec("chmod u+x
			// D:/Test/Default.properties");
		} catch (IOException ex) {
			ex.printStackTrace();
			return "error";
		}

		return "success";
	}

	public String runSh(String type, String fileName) {
		ProcessBuilder processBuilder = new ProcessBuilder();
		// ProcessBuilder pb = new ProcessBuilder("src/lexparser.sh",
		// "myArg1",
		// "myArg2");

		// linux
		// processBuilder.command("/app/setup/tonbeller/sironKYC/client/0001/data/input/Covert_BIDV/Covert_BIDV_run.sh");
		// -- Windows --

		// Run a command
		processBuilder.command("/app/setup/tonbeller/sironKYC/client/0001/data/input/VIB/VIB_Convert/VIB_Convert_run.sh");

		// Run a bat file
		// processBuilder.command("C:\\Users\\mkyong\\hello.bat");
		try {

			Process process = processBuilder.start();

			StringBuilder output = new StringBuilder();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				System.out.println("Success2!");
				System.out.println(output);
		
				String type_upload = fileName.substring(1, 2);
				if (type_upload.equals('P') || type_upload.equals('p')) {
					loadShFile(type, "PEP");
				} else if (type_upload.equals('E') || type_upload.equals('e')) {
					loadShFile(type, "Embargo");
				} else {
					System.out.println("cannot confirm!");
					return "error";
				}
			
			} else {
				// abnormal...
				System.out.println("err" + exitVal);
				return "error";
			}

		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	public String loadShFile(String type, String listType) {
		System.out.print("contenttype" + type + listType + "oooo");

		ProcessBuilder processBuilder = new ProcessBuilder();
		// ProcessBuilder pb = new ProcessBuilder("src/lexparser.sh", "myArg1",
		// "myArg2");

		// linux
		// processBuilder.command("D:/Test/Covert_BIDV_run.bat");
		// -- Windows --

		// Run a command
		if ("Full".equals(type)) {
			if (listType.equals("PEP")) {
				processBuilder.command(
						"/app/setup/tonbeller/sironKYC/client/0001/data/input/update_watchlist_VIB_full_pep.sh");
			} else {
				processBuilder.command(
						"/app/setup/tonbeller/sironKYC/client/0001/data/input/update_watchlist_VIB_full_emb.sh");
			}
		} else {
			if (listType.equals("PEP")) {
				processBuilder.command(
						"/app/setup/tonbeller/sironKYC/client/0001/data/input/update_watchlist_VIB_delta_pep.sh");
			} else {
				processBuilder.command(
						"/app/setup/tonbeller/sironKYC/client/0001/data/input/update_watchlist_VIB_delta_emb.sh");
			}
		}

		// Run a bat file
		// processBuilder.command("C:\\Users\\mkyong\\hello.bat");
		try {

			Process process = processBuilder.start();

			StringBuilder output = new StringBuilder();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				System.out.println("Success!");
				System.out.println(output);
				Session session = HibernateUtil.getSessionFactory().openSession();
				HttpServletRequest request = ServletActionContext.getRequest();

				ListFileService listFileService = new ListFileService(session);
				String userApprove = (String) ActionContext.getContext().getSession().get("userName");
				listFileService.updateDeleteData(request.getParameter("idName"), request.getParameter("inputData"),
						"confirmed", userApprove);
				TimeUnit.SECONDS.sleep(2);

				fileResult = getLatestFilefromDir("/app/setup/tonbeller/sironKYC/client/0001/data/log/tool/");

				// System.exit(0);
			} else {
				// abnormal...
				return "error";

			}

		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

}
