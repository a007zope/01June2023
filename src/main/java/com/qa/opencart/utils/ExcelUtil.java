package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	private static  final String TEST_DATA_SHEET_PATH ="./src/test/resources/testdata/opencartapptestdata.xlsx";
	private static  Workbook book;
	private static Sheet sheet;

	public static Object[][] getTestData(String sheetName)
	{

		String exceltestdata = System.getProperty("excelpath");

		FileInputStream ip = null;
		Object data[][]= null ;
		try {

			if(exceltestdata==null)
			{
				ip = new FileInputStream(TEST_DATA_SHEET_PATH);
			}

			else
			{
				switch(exceltestdata) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/testdata/opencartqaapptestdata.xlsx");
					System.out.println("$$$$$$$$$$$$$using QA data file $$$$$$$$$$$$$$");
					break;

				case "stage":
					System.out.println("$$$$$$$$$$$$$using STAGE data file $$$$$$$$$$$$$$");
					ip = new FileInputStream("./src/test/resources/testdata/opencartstageapptestdata.xlsx");
					break;

				default:
					System.out.println("please pass the correct test data path");
					break;
				}

			}

			book=WorkbookFactory.create(ip);
			sheet =book.getSheet(sheetName);


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

		for(int i =0;i<sheet.getLastRowNum();i++)
		{
			for(int j =0;j<sheet.getRow(0).getLastCellNum();j++)
			{
				data[i][j] =sheet.getRow(i+1).getCell(j).toString();
			}
		}
		return data;

	}



}
