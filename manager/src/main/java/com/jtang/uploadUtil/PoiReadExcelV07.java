package com.jtang.uploadUtil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 处理07及以后版本的excel表格，以xlsx为扩展名
 * @author minglong
 *
 */
public class PoiReadExcelV07 {
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
    
	public void testPoiExcel2007(String strPath) throws IOException, InvalidFormatException, ClassNotFoundException, SQLException {
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
		
        // 构造 XSSFWorkbook 对象，strPath 传入文件路径
		OPCPackage pkg = OPCPackage.open(strPath);
	    XSSFWorkbook xwb = new XSSFWorkbook(pkg);
	    
        // 读取第一章表格内容
        XSSFSheet sheet = xwb.getSheetAt(0);
        // 定义 row、cell
        XSSFRow row;
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
	    
        pkg.close(); // gracefully closes the underlying zip file
	}

}
