package com.jtang.dao.sqlUtil;

import java.util.ArrayList;

import com.jtang.dao.DaoUtil;

public class SimpleUpdator implements Updator {
	
	private String update;
	private String set;
	private String where;
	
	private Conditionor condition;
	
	private ArrayList<Object> setObjs;
	private ArrayList<Integer> setTypes;
	
	public SimpleUpdator (){
		super();
		setObjs = new ArrayList<Object>();
		setTypes = new ArrayList<Integer>();
	}
	
	public String toString(){
		return DaoUtil.concatStrings(update,set,where);
	}
	
	
	public Updator update(String table) {
		// TODO Auto-generated method stub
		update = "update " + table + " ";
		return this;
	}

	public Updator set(String colmun, String op, Object obj) {
		// TODO Auto-generated method stub
		if(set == null){ 
			set = " set ";
			if(op.toLowerCase().trim().equals("++")){
				set = set + colmun + " = " + colmun + " + ? ";
			}else if(op.toLowerCase().trim().equals("--")){
				set = set + colmun + " = " + colmun + " - ? ";
			}else
				set = set + colmun + " " + op + " ? ";
		}else{
			if(op.toLowerCase().trim().equals("++")){
				set = set +","  + colmun + " = " + colmun + " + ? ";
			}else if(op.toLowerCase().trim().equals("--")){
				set = set +","  + colmun + " = " + colmun + " - ? ";
			}else
				set = set +","  + colmun + " " + op + " ? ";
		}
		setObjs.add(obj);
		setTypes.add(DaoUtil.getSqlTypes(obj));
		return this;
	}


	public Object[] getParams() {
		// TODO Auto-generated method stub
		if(condition == null) return setObjs.toArray();
		return DaoUtil.concat(setObjs.toArray(), condition.getParams());
	}

	public int[] getTypes() {
		// TODO Auto-generated method stub
		if(condition == null) return DaoUtil.toPrimitive(setTypes.toArray(new Integer[setTypes.size()]));
		Integer[] t = new Integer[setTypes.size()];
		setTypes.toArray(t);
		return DaoUtil.toPrimitive(
				DaoUtil.concat(t, condition.getTypes())
				);
	}

	public Updator where(Conditionor condition) {
		// TODO Auto-generated method stub
		this.condition = condition;
		where = " where " + condition;
		return this;
	}

}
