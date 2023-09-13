package org.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils2 {

	static ReadExcel reader;

	static String Excel_Path = "/home/administrator/Documents/PG/API_Automate/src/main/java/org/excel/Api_Data_Sheet.xlsx";

	public static ArrayList<Object[]> getDataFromexcel() {
		ArrayList<Object[]> myData = new ArrayList<Object[]>();
		try {
			String Excel_Name = Excel_Path;
			reader = new ReadExcel(Excel_Name);
			System.out.println(Excel_Name);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int rowNum = 2; rowNum <= reader.getRowCount("Sheet1"); rowNum++) {
			String INDEX = reader.getCellData("Sheet1", "INDEX", rowNum);
			String Reason = reader.getCellData("Sheet1", "Reason", rowNum);
			String Mode_Of_Payment = reader.getCellData("Sheet1", "Mode_Of_Payment", rowNum);
			String Expected_Status_Code = reader.getCellData("Sheet1", "Expected_Status_Code", rowNum);
			String Expected_Reason = reader.getCellData("Sheet1", "Expected_Reason", rowNum);

			Object[] ob = { INDEX, Reason, Mode_Of_Payment, Expected_Status_Code, Expected_Reason };

			myData.add(ob);
		}
		return myData;

	}

	public static void writeinexcel_Reason(String value, int INDEX) throws Exception {
		Thread.sleep(1000);
		File fis = new File(Excel_Path);
//        String arr[]="https://www.google.com";
//        ArrayList<String> cars = new ArrayList<String>(fis);
		FileInputStream excelloc = new FileInputStream(fis);
		XSSFWorkbook wb = new XSSFWorkbook(excelloc);
		XSSFSheet s = wb.getSheetAt(2);
		XSSFRow row = s.getRow(INDEX);
		XSSFCell c = row.createCell(6);
		c.setCellValue(value);
		FileOutputStream out = new FileOutputStream(fis);
		wb.write(out);
		out.close();
	}

	public static void writeinexcel_Reason_Status(String value, int INDEX) throws Exception {
		Thread.sleep(1000);
		File fis = new File(Excel_Path);
//        String arr[]="https://www.google.com";
//        ArrayList<String> cars = new ArrayList<String>(fis);
		FileInputStream excelloc = new FileInputStream(fis);
		XSSFWorkbook wb = new XSSFWorkbook(excelloc);
		XSSFSheet s = wb.getSheetAt(2);
		XSSFRow row = s.getRow(INDEX);
		XSSFCell c = row.createCell(8);
		c.setCellValue(value);
		FileOutputStream out = new FileOutputStream(fis);
		wb.write(out);
		out.close();
	}

	public static void writeinexcel_Code(String value, int INDEX) throws Exception {
		Thread.sleep(1000);
		File fis = new File(Excel_Path);
//        String arr[]="https://www.google.com";
//        ArrayList<String> cars = new ArrayList<String>(fis);
		FileInputStream excelloc = new FileInputStream(fis);
		XSSFWorkbook wb = new XSSFWorkbook(excelloc);
		XSSFSheet s = wb.getSheetAt(2);
		XSSFRow row = s.getRow(INDEX);
		XSSFCell c = row.createCell(5);
		c.setCellValue(value);
		FileOutputStream out = new FileOutputStream(fis);
		wb.write(out);
		out.close();
	}

	public static void writeinexcel_Code_Status(String value, int INDEX) throws Exception {
		Thread.sleep(1000);
		File fis = new File(Excel_Path);
//        String arr[]="https://www.google.com";
//        ArrayList<String> cars = new ArrayList<String>(fis);
		FileInputStream excelloc = new FileInputStream(fis);
		XSSFWorkbook wb = new XSSFWorkbook(excelloc);
		XSSFSheet s = wb.getSheetAt(2);
		XSSFRow row = s.getRow(INDEX);
		XSSFCell c = row.createCell(7);
		c.setCellValue(value);
		FileOutputStream out = new FileOutputStream(fis);
		wb.write(out);
		out.close();
	}

	public static void writeinexcelForReasons(String value, int INDEX) throws Exception {
		Thread.sleep(1500);
		File fis = new File(Excel_Path);
		FileInputStream excelloc = new FileInputStream(fis);
		XSSFWorkbook wb = new XSSFWorkbook(excelloc);
		XSSFSheet s = wb.getSheetAt(0);
		XSSFRow row = s.getRow(INDEX);
		XSSFCell c = row.createCell(11);
		c.setCellValue(value);
		FileOutputStream out = new FileOutputStream(fis);
		wb.write(out);
		out.close();

	}

}
