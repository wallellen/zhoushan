package com.jtang.uploadUtil;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class PoiReadExcel {
	public static void read(String strPath){
		//处理xls扩展名
		if(strPath.endsWith("xls")){
			PoiReadExcelV03 poiReadExcelV03=new PoiReadExcelV03();
			try {
				poiReadExcelV03.testPoiExcel2003(strPath);
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(strPath.endsWith("xlsx")){
			//处理xlsx扩展名
			PoiReadExcelV07 poiReadExcelV07=new PoiReadExcelV07();
			try {
				poiReadExcelV07.testPoiExcel2007(strPath);
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
