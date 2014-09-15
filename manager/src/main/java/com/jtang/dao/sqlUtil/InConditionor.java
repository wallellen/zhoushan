package com.jtang.dao.sqlUtil;

import java.util.List;

/**
 * 支持where id in (1,2)类似用法
 * @author Administartor
 *
 */
public class InConditionor extends SimpleConditionor {

	private int objNumber = 0;
	
	@SuppressWarnings("unchecked")
	public InConditionor(String colmunName, Object content) {
		// TODO Auto-generated constructor stub
		super(colmunName,"in",content);
		if(!(content instanceof Selector)){
			objNumber = ((List<Object>)content).size();
		}
	}
	
	public String toString(){
		if(content instanceof Selector) {
			return super.toString();
		}
		StringBuilder sb = new StringBuilder("");
		for(int i = 1; i <= bracketsNum; i++){
			sb.append("(");
		}
		sb.append(columnName + " " + operator + " (");
		for(int i = 0; i < objNumber; i++){
			sb.append(i == objNumber - 1 ? "?" : "?,");
		}
		sb.append(" ) ");
		if(next == null){
			for(int i = 1; i <= bracketsOver; i++){
				sb.append(")");
			}
			return sb.toString();
		}
		for(int i = 1; i <= bracketsOver; i++){
			sb.append(")");
		}
		sb.append(" ");
		sb.append(logic + " " + next);
		return sb.toString();
	}
	
}
