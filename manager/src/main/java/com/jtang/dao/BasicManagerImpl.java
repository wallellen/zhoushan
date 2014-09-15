package com.jtang.dao;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.jtang.annotation.DBTable;
import com.jtang.annotation.TableColumn;
import com.jtang.dao.sqlUtil.Deletor;
import com.jtang.dao.sqlUtil.Insertor;
import com.jtang.dao.sqlUtil.Selector;
import com.jtang.dao.sqlUtil.Updator;
import com.jtang.model.Sensor;

public class BasicManagerImpl extends JdbcDaoSupport implements BasicManagerDao {

	DaoUtil du = new DaoUtil();
	
	public int queryInt(String sql, Object[] args, int[] types) {
		// TODO Auto-generated method stub
		Integer i = this.getJdbcTemplate().queryForObject(sql, args,types,Integer.class);
		if(i == null) i=0;
		return i;
	}

	public List<Map<String, Object>> queryForListMap(String sql, Object[] args,
			int[] types) {
		// TODO Auto-generated method stub
		return this.getJdbcTemplate().queryForList(sql, args, types);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> query(String sql, Object[] args, int[] types, String mapperClassName) {
		// TODO Auto-generated method stub
        
		try {
			return this.getJdbcTemplate().query(sql, args,types ,(RowMapper<T>)( Class.forName(mapperClassName).newInstance()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	public int add(String sql, Object[] args,int [] argTypes) {
		// TODO Auto-generated method stub
		//return the number of rows affected
		return executeUpdate(sql, args,argTypes);

	}

	public int update(String sql, Object[] args, int[] argTypes) {
		// TODO Auto-generated method stub
		return executeUpdate(sql, args,argTypes);
	}
	
	public int delete(String sql, Object[] args, int[] argTypes) {
		// TODO Auto-generated method stub
		return executeUpdate(sql, args,argTypes);
	}
	
	private int executeUpdate(String sql,Object[] args,int[] argTypes){
		if(argTypes == null){
			return this.getJdbcTemplate().update(sql, args);
		}else{
			return this.getJdbcTemplate().update(sql, args,argTypes);
		}
	}
	
	

	public int addAuto(Object obj,String... args) {
		// TODO Auto-generated method stub
		//return the number of rows affected
		return executeUpdate(du.getInsertSql(obj, args),du.getArgValues(obj, args),du.getArgTypes(obj, args));

	}
	
	
	/**
	 * 以后开发
	 * @param obj
	 * @param updateFileds
	 * @param incrementFileds
	 * @param conditionFileds
	 * @return
	 */
	public int customUpdate(Object obj,String [] updateFileds,String [] incrementFileds,String [] conditionFileds){
		return 0;
	}
	
	public int updateAuto(Object obj,String...args ){
		List<String> exception = du.convertArrayToList(args);
		String sql = du.getUpdateSql(obj, args);
		
		Class<?> targetClass = obj.getClass();
		DBTable tablePro = targetClass.getAnnotation(DBTable.class);
		String primaryKeyName = tablePro.primaryKeyName();
		if(exception == null) exception = new ArrayList<String>();
		exception.add(primaryKeyName);
		
		String [] argsNew = new String[exception.size()];
		exception.toArray(argsNew);
		
		Object[] argsValue = du.getArgValues(obj, argsNew);
		int [] argsTypes = du.getArgTypes(obj, argsNew);
		
		String primaryGetter = "get" + primaryKeyName.substring(0,1).toUpperCase() + primaryKeyName.substring(1);
		Method primaryMethod = null;
		try {
			primaryMethod = targetClass.getDeclaredMethod(primaryGetter);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Object lastObject = null;
		try {
			lastObject = primaryMethod.invoke(obj);
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
		
		Object[] argsValueNew = new Object[argsValue.length + 1];
		System.arraycopy(argsValue, 0, argsValueNew, 0, argsValue.length);
		argsValueNew[argsValue.length] = lastObject;
		
		int lastType = 0;
		try {
			Field f = targetClass.getDeclaredField(primaryKeyName);
			TableColumn tc = f.getAnnotation(TableColumn.class);
			lastType = tc.type();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int [] argsTypesNew = new int[argsTypes.length + 1];
		System.arraycopy(argsTypes, 0, argsTypesNew, 0, argsTypes.length);
		argsTypesNew[argsTypes.length] = lastType;

		return  executeUpdate(sql, argsValueNew,argsTypesNew);
	}
	
	
	
	
	public static void main(String [] args){
		Sensor s  = new Sensor();
		s.setExtAddr("gfg");
		new BasicManagerImpl().updateAuto(s,"shortAddr");
		//System.out.println(new BasicManagerImpl().getUpdateSql(s,new String[]{"shortAddr"}));
	}

	public <T> List<T> query(Selector sel, String mapperClassName) {
		// TODO Auto-generated method stub
		return query(sel.toString(),sel.getParams(),sel.getTypes(),mapperClassName);
	}

	public int queryInt(Selector sel) {
		// TODO Auto-generated method stub
		return queryInt(sel.toString(),sel.getParams(),sel.getTypes());
	}

	public int update(Updator up) {
		// TODO Auto-generated method stub
		return update(up.toString(), up.getParams(), up.getTypes());
	}

	public List<Map<String, Object>> queryForList(Selector sel) {
		// TODO Auto-generated method stub
		return queryForListMap(sel.toString(), sel.getParams(), sel.getTypes());
	}

	public int add(Insertor in) {
		// TODO Auto-generated method stub
		return add(in.toString(), in.getParams(), in.getTypes());
	}

	
	public int delete(Deletor del) {
		// TODO Auto-generated method stub
		return delete(del.toString(), del.getParams(), del.getTypes());
	}

	public <T> List<T> queryForList(String sql, Object[] args, Class<T> type) {
		// TODO Auto-generated method stub
		return (List<T>) this.getJdbcTemplate().queryForList(sql, args, type);
	}

}
