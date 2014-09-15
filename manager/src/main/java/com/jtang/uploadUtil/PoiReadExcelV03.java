package com.jtang.uploadUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;

/**
 * 处理03及以前版本的excel表格，以xls为扩展名
 * @author chenminglong
 *
 */
public class PoiReadExcelV03 {
	/** 
     * 获取单元格中的值 
     *  
     * @param cell 单元格 
     * @return 
     */  
    private static Object getCellValue(Cell cell) {  
        int type = cell.getCellType();  
        switch (type) {  
        case Cell.CELL_TYPE_STRING:  
            return (Object) cell.getStringCellValue();  
        case Cell.CELL_TYPE_NUMERIC:  
            Double value = cell.getNumericCellValue();  
            return (Object) (value.intValue());  
        case Cell.CELL_TYPE_BOOLEAN:  
            return (Object) cell.getBooleanCellValue();  
        case Cell.CELL_TYPE_FORMULA:  
            return (Object) cell.getArrayFormulaRange().formatAsString();  
        case Cell.CELL_TYPE_BLANK:  
            return (Object) "";  
        default:  
            return null;  
        }  
    }  
    
	public void testPoiExcel2003(String strPath) throws IOException, InvalidFormatException, ClassNotFoundException, SQLException {
		Properties props = new Properties();
		String key1="url";
		String key2="account";
		String key3="password";
		String value1=null;
		String value2=null;
		String value3=null;
        try {
        	//读取数据库配置文件database.properties，此时为实验数据库excel2mysql
        	InputStream in = getClass().getResourceAsStream("/database.properties");
        	props.load(in);

        	value1 = props.getProperty (key1);
        	value2 = props.getProperty (key2);
        	value3 = props.getProperty (key3);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
		Class.forName("com.mysql.jdbc.Driver");
    	Connection con = (Connection) DriverManager.getConnection(value1, value2, value3);
    	// 关闭事务自动提交
    	con.setAutoCommit(false);
    	PreparedStatement pst = (PreparedStatement) con.prepareStatement("insert into transportation_record values (?,?,?,?,?,?,?,?,?)");
		
    	//把一张xls的数据表读到wb里
		HSSFWorkbook wb = null;
		FileInputStream input=new FileInputStream(new File(strPath));
		
		try {
			wb = new HSSFWorkbook(input);
			//读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作    
			HSSFSheet sheet = wb.getSheetAt(0);
			// 定义 row、cell
			HSSFRow row;
			Cell cell;
			String cellStr;
			// 循环输出表格中的内容
			for (int i = sheet.getFirstRowNum()+1; i < sheet.getPhysicalNumberOfRows(); i++) {
			    row = sheet.getRow(i);
			    //cell从0计数，pst参数从1计数
			    for(int j=0;j<3;j++){
			    	cellStr = row.getCell(j).toString();
			    	System.out.print(cellStr + "\t");
			    	pst.setString(j+1, cellStr);
			    }
			    //运输数据：出发和到达时间
			    for(int j=3;j<5;j++){
			    	Date date = row.getCell(j).getDateCellValue();
			    	System.out.print(date + "\t");
			    	pst.setTimestamp(j+1, new java.sql.Timestamp(date.getTime()));
			    }
			    //运输状态，0/1/2
			    cell = row.getCell(5);
			    pst.setInt(6, (Integer)getCellValue(cell));	
			    System.out.print(cell + "\t");
			    
			    //企业ID，数据库中位字符串型，实际为整数，如：1000
			    cell = row.getCell(6);
			    pst.setString(7, getCellValue(cell).toString());	
			    System.out.print(cell + "\t");
			    
			    for(int j=7;j<9;j++){
			    	cellStr = row.getCell(j).toString();
			    	System.out.print(cellStr + "\t");
			    	pst.setString(j+1, cellStr);
			    }    
		    	System.out.println();
				pst.addBatch();
			}  
			// 执行批量更新
			pst.executeBatch();
			// 语句执行完毕，提交本事务
			con.commit();

			pst.close();
			con.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			input.close();
		}
	    
	}

}
