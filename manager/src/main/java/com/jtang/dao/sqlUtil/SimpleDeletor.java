package com.jtang.dao.sqlUtil;

import com.jtang.dao.DaoUtil;

public class SimpleDeletor implements Deletor {

	private String deleteFrom;
	private String where;
	
	private Conditionor condition;
	
	public String toString(){
		return DaoUtil.concatStrings(deleteFrom,where);
	}
	
	public Deletor deleteFrom(String table) {
		// TODO Auto-generated method stub
		deleteFrom = "delete from " + table + " ";
		return this;
	}

	public Deletor where(Conditionor condition) {
		// TODO Auto-generated method stub
		this.condition = condition;
		where = " where " + condition;
		return this;
	}

	public Object[] getParams() {
		// TODO Auto-generated method stub
		if(condition == null) return null;
		return condition.getParams();
	}

	public int[] getTypes() {
		// TODO Auto-generated method stub
		if(condition == null) return null;
		return condition.getIntTypes();
	}


}
