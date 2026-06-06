package org.example;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelFactory {
    public static ArrayList readDataExcel(String strPath){
        ArrayList<ArrayList> arrAllValue = new ArrayList<>();
        FileInputStream file;
        try {
            file = new FileInputStream(strPath);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();

            //get value
            for(int i = 0; i <= sheet.getLastRowNum(); i++) {
                ArrayList<String> arrValue = new ArrayList<>();
                Row row = sheet.getRow(i);
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    String strValue = dataFormatter.formatCellValue(row.getCell(j));
                    arrValue.add(strValue);
                }
                arrAllValue.add(arrValue);
            }

            workbook.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return arrAllValue;
    }
}
