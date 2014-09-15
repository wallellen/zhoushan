package com.jtang.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.jtang.annotation.DBTable;
import com.jtang.annotation.TableColumn;
import com.jtang.dao.sqlUtil.Conditionor;
import com.jtang.dao.sqlUtil.Selector;
import com.jtang.dao.sqlUtil.Zql;
import com.jtang.model.PageInfo;

public class DaoUtil {
	/**
	 * 该方法只能返回根据主键update的sql语句，而且不支持set a = a + 1类似的高级功能
	 * @param obj
	 * @param args
	 * @return
	 */
	public String getUpdateSql(Object obj,String [] args){
		List<String> exception = convertArrayToList(args);
		String sql = "";
		Class<?> targetClass = obj.getClass();
		DBTable tablePro = targetClass.getAnnotation(DBTable.class);
		if(tablePro != null){
			if(exception == null) exception = new ArrayList<String>();
			exception.add(tablePro.primaryKeyName());
			sql = "update " + tablePro.tableName() + " set ";
		}else{
			throw new RuntimeException();
		}
		
		int count = 0;
		for(Field f : targetClass.getDeclaredFields()){
			
			TableColumn tableColumn = f.getAnnotation(TableColumn.class);
			if(tableColumn != null){
				if(exception != null && exception.contains(f.getName())){
					continue;
				}
				if(count == 0){
					sql += tableColumn.columnName() + " = ? ";
				}else{
					sql += "," + tableColumn.columnName() + " = ? ";
				}
				count++;	
			}
		}
		
		sql += " where " + tablePro.primaryKeyName() + " = ?";
		
		return sql;
	}
	
	public String getInsertSql(Object obj,String[] args) {
		// TODO Auto-generated method stub
		List<String> exception = convertArrayToList(args);
		String sql = "";
		Class<?> targetClass = obj.getClass();
		DBTable tableName = targetClass.getAnnotation(DBTable.class);
		if(tableName != null){
			sql += "insert into " + tableName.tableName() + "(";
		}else{
			throw new RuntimeException();
		}
		
		

		int count = 0;
		for(Field f : targetClass.getDeclaredFields()){
			
			TableColumn tableColumn = f.getAnnotation(TableColumn.class);
			if(tableColumn != null){
				if(exception != null && exception.contains(f.getName())){
					continue;
				}
				if(count == 0){
					sql += tableColumn.columnName();
				}else{
					sql += "," + tableColumn.columnName() ;
				}
				count++;	
			}
		}
		sql += ") values(";
		for(int i = 0; i < count ; i++){
			sql += (i == count -1?"?":"?,");
		}
		sql += ")";
		return sql;
	}
	
	public  List<String> convertArrayToList(String[] args) {
		// TODO Auto-generated method stub
		if(args == null || args.length == 0){
			return null;
		}
		ArrayList<String> result = new ArrayList<String>();
		for(String s : args){
			result.add(s);
		}
		return result;
	}
	
	/**
	 * 
	 * @param obj the source object
	 * @param args except these fields
	 * @return the fields's type in db
	 */
	public  int[] getArgTypes(Object obj,String[] args){
		List<String> exception = convertArrayToList(args);
		Class<?> targetClass = obj.getClass();
		List<Integer> result = new ArrayList<Integer>();
		for(Field f : targetClass.getDeclaredFields()){
			TableColumn tableColumn = f.getAnnotation(TableColumn.class);
			if(tableColumn != null){
				if(exception != null && exception.contains(f.getName())){
					continue;
				}
				result.add(tableColumn.type());	
			}
		}
		Integer [] t = new Integer[result.size()];
		result.toArray(t);
		return toPrimitive( t);
	}
	
	/**
	 * 
	 * @param obj the source object
	 * @param args except these fields
	 * @return the fields's value
	 */
	public  Object[] getArgValues(Object obj,String [] args) {
		List<String> exception = convertArrayToList(args);
		Class<?> targetClass = obj.getClass();
		List<Object> result = new ArrayList<Object>();
		for(Field f : targetClass.getDeclaredFields()){
			TableColumn tableColumn = f.getAnnotation(TableColumn.class);
			if(tableColumn != null){
				String fieldNane = f.getName();
				if(exception != null && exception.contains(fieldNane)){
					continue;
				}
				String methodName = "get" + fieldNane.substring(0,1).toUpperCase() + fieldNane.substring(1);
				Method m;
				try {
					m = targetClass.getMethod(methodName, (Class<?>[])null);
					result.add(m.invoke(obj));
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}

		return result.toArray();
	}
	
	
	
	
	/**
	 * Convert Integer[] to int[]
	 * @param IntegerArray
	 * @return
	 */
	public static int[] toPrimitive(Integer[] IntegerArray) {
		 
		int[] result = new int[IntegerArray.length];
		for (int i = 0; i < IntegerArray.length; i++) {
			result[i] = IntegerArray[i].intValue();
		}
		
		return result;
	}
	
	/**
	 * 将非空的字符串加起来
	 * @param items，能接收多个String
	 * @return 将参数中所有非null的字符串按顺序加起来
	 */
	public static String concatStrings(String... items){
		String result = "";
		for(String item : items){
			if(item != null){
				result += item;
			}
		}
		return result;
	}
	
	public static Object[] concat(Object[] A, Object[] B) {
		Object[] C= new Object[A.length + B.length];
		System.arraycopy(A, 0, C, 0, A.length);
		System.arraycopy(B, 0, C, A.length, B.length);
		return C;
	}
	
	public static Integer[] concat(Integer[] A, Integer[] B) {
		Integer[] C= new Integer[A.length + B.length];
		System.arraycopy(A, 0, C, 0, A.length);
		System.arraycopy(B, 0, C, A.length, B.length);
		return C;
	}
	
	public static int[] concat(int[] A, int[] B) {
		int[] C= new int[A.length + B.length];
		System.arraycopy(A, 0, C, 0, A.length);
		System.arraycopy(B, 0, C, A.length, B.length);
		return C;
	}
	
	/**
	 * Return the sql type of a object
	 * @param obj
	 * @return
	 */
	public static Integer getSqlTypes(Object obj){
		if(obj instanceof Integer){
			return Types.INTEGER;
		}else if(obj instanceof Float){
			return Types.FLOAT;
		}else if(obj instanceof Double){
			return Types.DOUBLE;
		}else if(obj instanceof Long){
			return Types.BIGINT;
		}else if(obj instanceof Short){
			return Types.SMALLINT;
		}else if(obj instanceof Date
				||
				obj instanceof java.util.Date){
			return Types.DATE;
		}
		else{
			return Types.VARCHAR;
		}
	}
	
	/**
	 * 该方法返回分页所需要的Selector
	 * @param pageInfo
	 * @param tableName
	 * @param con
	 * @param manager
	 * @return
	 */
	public static <T> Selector getSelectorByPage(PageInfo<T> pageInfo,String tableName,Conditionor con,BasicManagerDao manager){
		Selector sel = Zql.select("*")
				 .from(tableName);
		int allCounts = 0;
		if(pageInfo.getPageNum() == 0){
			allCounts = manager.queryInt(Zql.select("count(*)")
					.from(tableName).where(con));
			pageInfo.setTotalCount(allCounts);
			pageInfo.setPageNum((int)Math.ceil(pageInfo.getTotalCount()*1.0/pageInfo.getRecordsPP()));
		}
		boolean flag = false;
		if(pageInfo.getRecordsPP() == -1){
			flag = true;
			if(allCounts == 0) allCounts = manager.queryInt(Zql.select("count(*)")
					.from(tableName).where(con));
			pageInfo.setRecordsPP(allCounts);
			pageInfo.setTotalCount(allCounts);
			pageInfo.setPageNum(1);
		}
		sel.where(con);
		sel.limit( (pageInfo.getCurrentPage() - 1) * pageInfo.getRecordsPP(), pageInfo.getRecordsPP());
		if(flag){
			pageInfo.setRecordsPP(-1);
		}
		return sel;		
	}
}
