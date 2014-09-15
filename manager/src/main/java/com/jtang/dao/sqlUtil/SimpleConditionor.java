package com.jtang.dao.sqlUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.jtang.dao.DaoUtil;

public class SimpleConditionor implements Conditionor {
	protected String columnName;
	protected String operator;
	protected Object content;
	
	protected String logic;
	
	private List<Object> params;
	
	protected Conditionor next = null;
	protected int bracketsNum = 0;
	protected int bracketsOver = 0;
	public SimpleConditionor(String colmunName,String operator,Object content){
		this.columnName = colmunName;
		this.operator = operator;
		this.content = content;
	}
	
	public String toString(){
		//if(brackets) return toRString();
		StringBuilder sb = new StringBuilder("");
		for(int i = 1; i <= bracketsNum; i++){
			sb.append("(");
		}
		sb.append(columnName + " " + operator);
		if(content instanceof Selector){
			sb.append(" (");
			sb.append(((Selector)content).toString());
			sb.append(") ");
		}else {
			sb.append(" ? ");
		}
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
	
	public Conditionor and(Conditionor next) {
		// TODO Auto-generated method stub
		if(next == null) return this;
		Conditionor tail = this;
		while(tail.next() != null){
			tail = tail.next();
		}
		tail.setLogic("and");
		tail.setNext(next);
		
		return this;
	}
	
	public Conditionor or(Conditionor next) {
		// TODO Auto-generated method stub
		if(next == null) return this;
		Conditionor tail = this;
		while(tail.next() != null){
			tail = tail.next();
		}
		tail.setLogic("or");
		tail.setNext(next);
		return this;
	}
	public Object getContent() {
		// TODO Auto-generated method stub
		return content;
	}
	public Conditionor next() {
		// TODO Auto-generated method stub
		return next;
	}
	@SuppressWarnings("unchecked")
	public Object[] getParams() {
		// TODO Auto-generated method stub
		if(params != null) return params.toArray();
		Conditionor head = this;
		params = new ArrayList<Object>();
		while(head != null){
			if(head.getContent() instanceof List<?>){
				params.addAll((List<? extends Object>) head.getContent());
			}else if(head.getContent() instanceof Selector){
				params.addAll(Arrays.asList(((Selector)head.getContent()).getParams()));
			}else{
				params.add(head.getContent());
			}
			head = head.next();
		}
		return params.toArray();
	}
	
	public int[] getIntTypes(){
		return DaoUtil.toPrimitive(getTypes());
	}
	
	public Integer[] getTypes() {
		// TODO Auto-generated method stub
		if(params == null) getParams();
		ArrayList<Integer> types = new ArrayList<Integer>();
		for(Object obj : params){
			types.add(DaoUtil.getSqlTypes(obj));
		}
		Integer [] t = new Integer[types.size()];
		types.toArray(t);
		return t;
	}

	/**
	 * @return the logic
	 */
	public String getLogic() {
		return logic;
	}

	/**
	 * @param logic the logic to set
	 */
	public void setLogic(String logic) {
		this.logic = logic;
	}

	/**
	 * @return the next
	 */
	public Conditionor getNext() {
		return next;
	}

	/**
	 * @param next the next to set
	 */
	public void setNext(Conditionor next) {
		this.next = next;
	}

	public Conditionor brackets() {
		// TODO Auto-generated method stub
		bracketsNum++;
		Conditionor tail = this;
		while(tail.next() != null) tail = tail.next();
		tail.bracketsOver();
		return this;
	}
	
	public void bracketsOver(){
		this.bracketsOver++;
	}
}
