package org.o7planning.load_ds_wl.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import com.opensymphony.xwork2.ActionSupport;

public class ExcelReading extends ActionSupport {
	public String convert() throws InvalidFormatException, IOException {
		convertExcelToCSV("Book1.xlsx");
		return "success";

	}

	public static void convertExcelToCSV(String fileName) throws InvalidFormatException, IOException {
		BufferedWriter output = new BufferedWriter(
				new FileWriter(fileName.substring(0, fileName.lastIndexOf(".")) + ".csv"));

		InputStream is = new FileInputStream(new File("D:/Test/Book1.xlsx"));

		Workbook wb = WorkbookFactory.create(is);

		Sheet sheet = wb.getSheetAt(0);

		// hopefully the first row is a header and has a full compliment of
		// cells, else you'll have to pass in a max (yuck)
		int maxColumns = sheet.getRow(0).getLastCellNum();

		for (Row row : sheet) {

			// row.getFirstCellNum() and row.getLastCellNum() don't return the
			// first and last index when the first or last column is blank
			int minCol = 0; // row.getFirstCellNum()
			int maxCol = maxColumns; // row.getLastCellNum()

			for (int i = minCol; i < maxCol; i++) {

				Cell cell = row.getCell(i);
				String buf = "";
				if (i > 0) {
					buf = ",";
				}

				if (cell == null) {
					output.write(buf);
					// System.out.print(buf);
				} else {

					String v = null;

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						v = cell.getRichStringCellValue().getString();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							v = cell.getDateCellValue().toString();
						} else {
							v = String.valueOf(cell.getNumericCellValue());
						}
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						v = String.valueOf(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_FORMULA:
						v = cell.getCellFormula();
						break;
					default:
					}

					if (v != null) {
						buf = buf + toCSV(v);
					}
					output.write(buf);
					// System.out.print(buf);
				}
			}

			output.write("\n");
			// System.out.println();
		}
		is.close();
		output.close();

	}

	public static String toCSV(String value) {

		String v = null;
		boolean doWrap = false;

		if (value != null) {

			v = value;

			if (v.contains("\"")) {
				v = v.replace("\"", "\"\""); // escape embedded double quotes
				doWrap = true;
			}

			if (v.contains(",") || v.contains("\n")) {
				doWrap = true;
			}

			if (doWrap) {
				v = "\"" + v + "\""; // wrap with double quotes to hide the
										// comma
			}
		}

		return v;
	}
}
