package com.jtang.dao.sqlUtil;

import java.util.ArrayList;

import com.jtang.dao.DaoUtil;

public class SimpleInsertor implements Insertor {

	private String insert;
	private String keys;
	private String values;
	
	private ArrayList<Object> setObjs;
	private ArrayList<Integer> setTypes;
	
	public String toString(){
		return DaoUtil.concatStrings(insert,keys+")",values+")");
	}
	
	public SimpleInsertor(){
		super();
		setObjs = new ArrayList<Object>();
		setTypes = new ArrayList<Integer>();
	}
	
	public Insertor insertInto(String table) {
		// TODO Auto-generated method stub
		insert = "insert into " + table + " ";
		return this;
	}

	public Insertor keyValue(String key, Object value) {
		// TODO Auto-generated method stub
		if(keys == null) {
			keys = " (" + key;
		}else{
			keys += "," + key;
		}
		
		if(values == null) {
			values = " values (" + "?";
		}else{
			values += "," + "?";
		}
		setObjs.add(value);
		setTypes.add(DaoUtil.getSqlTypes(value));
		return this;
	}

	public Object[] getParams() {
		// TODO Auto-generated method stub
		return setObjs.toArray();
	}

	public int[] getTypes() {
		// TODO Auto-generated method stub
		return DaoUtil.toPrimitive(setTypes.toArray(new Integer[setTypes.size()]));
	}

}
