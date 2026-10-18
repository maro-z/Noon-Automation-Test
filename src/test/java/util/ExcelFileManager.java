package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExcelFileManager {
    private final Logger log = LogManager.getLogger(ExcelFileManager.class);
    public XSSFWorkbook workbook;
    private HashMap<String,XSSFSheet> sheets;
    public ExcelFileManager(String filepath){
        try {
            log.debug("trying to open excel file: {}",filepath);
            sheets = new HashMap<>();
            FileInputStream fileInputStream = new FileInputStream(filepath);
            workbook = new XSSFWorkbook(fileInputStream);
            for (int i=0;i<workbook.getNumberOfSheets();i++){
                sheets.put(workbook.getSheetName(i),workbook.getSheetAt(i));
                log.debug("Sheet: {} was read successfully",workbook.getSheetName(i));
            }
        }catch (Exception e){
            log.error("error in opening the excel file: {}",e.toString());
        }
    }
    public int getRowsCount(String sheetName){
        log.debug("getting number of rows in sheet: {}",sheetName);
        return sheets.get(sheetName).getPhysicalNumberOfRows();
    }
    public int getColumnsCount(String sheetName){
        log.debug("getting number of columns in sheet: {}",sheetName);
        return sheets.get(sheetName).getRow(0).getPhysicalNumberOfCells();
    }
    public String getCellValue(int rowIndex, int colIndex,String sheetName){
        log.debug("getting value in cell: {},{} in sheet: {}",rowIndex,colIndex,sheetName);
        Cell cell = sheets.get(sheetName).getRow(rowIndex).getCell(colIndex);
        DataFormatter dataFormatter = new DataFormatter();
        return dataFormatter.formatCellValue(cell);
    }
    public List<String> getColumnValues(int colIndex, String sheetName){
        log.debug("getting all values in column: {} in sheet: {}",colIndex,sheetName);
        int numberOfRows = getRowsCount(sheetName);
        ArrayList<String> data = new ArrayList<>();
        for (int i=1;i<numberOfRows;i++){
            data.add(getCellValue(i,colIndex,sheetName));
        }
        return data;
    }
    public List<String> getRowValues(int rowIndex, String sheetName){
        log.debug("getting all values in row: {} in sheet: {}",rowIndex,sheetName);
        int numberOfCols = getColumnsCount(sheetName);
        ArrayList<String> data = new ArrayList<>();
        for (int i=0;i<numberOfCols;i++){
            data.add(getCellValue(rowIndex,i,sheetName));
        }
        return data;
    }

}
