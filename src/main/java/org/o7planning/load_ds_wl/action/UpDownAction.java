package org.o7planning.load_ds_wl.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.opencsv.CSVReader;
import com.opensymphony.xwork2.ActionSupport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hslf.model.Sheet;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

public class UpDownAction extends ActionSupport {
	private static final long serialVersionUID = 7299264265184515893L;

	@Override
	public String execute() {

		return "success";
	}

	public String executeShFile() {
		HttpServletRequest request = ServletActionContext.getRequest();

		String outputcsv = request.getParameter("idName");
		String type = request.getParameter("idType");
		System.out.println("FileName" + outputcsv + type);

		// String scriptContent = "#!/bin/bash
		// \nexcelPath=D:/Test/E_VIB01072020.xlsx
		// \noutputCSV=D:/workspace/load_ds_wl/output.csv";
		String scriptContent = "#!/bin/bash \nexcelPath=D:/Test/Upload/" + outputcsv
				+ "\noutputCSV=D:/workspace/load_ds_wl/output.csv";

		try {
			Writer output = new BufferedWriter(new FileWriter(
					"D:/workspace/load_ds_wl/Covert_BIDV/blitzgrp/covert_bidv_0_1/contexts/Default.properties"));
			output.write(scriptContent);
			output.close();
			runSh(type);

			// Runtime.getRuntime().exec("chmod u+x
			// D:/Test/Default.properties");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return "success";
	}

	public String runSh(String type) {
		ProcessBuilder processBuilder = new ProcessBuilder();
		// ProcessBuilder pb = new ProcessBuilder("src/lexparser.sh",
		// "myArg1",
		// "myArg2");

		// linux
		// processBuilder.command("D:/Test/Covert_BIDV_run.bat");
		// -- Windows --

		// Run a command
		processBuilder.command("cmd.exe", "/c", "D:/workspace/load_ds_wl/Covert_BIDV/Covert_BIDV_run.bat");

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
				Reader readeri = Files.newBufferedReader(Paths.get("D:/workspace/load_ds_wl/output.csv"));
				CSVReader csvReader = new CSVReader(readeri);
				// Reading Records One by One in a String array
				String[] nextRecord;
				int i = 0;
				while ((nextRecord = csvReader.readNext()) != null && i < 2) {
					System.out.println("Name : " + nextRecord[i]);
					System.out.println("id : " + i);

					if (i == 1) {
						if (nextRecord[1].contains("PEP")) {
							loadShFile(type, "PEP");
						} else {
							loadShFile(type, "Embargo");
						}
					}
					i++;
				}
				// FileInputStream input = new FileInputStream(file);
				// POIFSFileSystem fs = new POIFSFileSystem(input);
				// HSSFWorkbook wb = new HSSFWorkbook(fs);
				// HSSFSheet sheeth = wb.getSheetAt(0);
				// Row row = sheeth.getRow(1);
				// String content = row.getCell(1).getStringCellValue();
				// System.out.print("content" + content);

				// return "success";
				// System.exit(0);
			} else {
				// abnormal...
				System.out.println("err" + exitVal);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
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
		if ("full".equals(type)) {
			if (listType.equals("PEP")) {
				processBuilder.command("cmd.exe", "/c", "D:/Test/update_watchlist_VIB_full_pep.sh");
			} else {
				processBuilder.command("cmd.exe", "/c", "D:/Test/update_watchlist_VIB_full_emb.sh");
			}
		} else {
			if (listType.equals("PEP")) {
				processBuilder.command("cmd.exe", "/c", "D:/Test/update_watchlist_VIB_delta_pep.sh");
			} else {
				processBuilder.command("cmd.exe", "/c", "D:/Test/update_watchlist_VIB_delta_emb.sh");
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
				// System.exit(0);
			} else {
				// abnormal...
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "success";
	}
}
