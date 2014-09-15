package com.jtang.dao.sqlUtil;

import java.util.ArrayList;
import java.util.List;

import com.jtang.dao.DaoUtil;


/**
 * 该类仅仅支持简单的sql语句生成，如需更多的功能，继承它扩展功能即可
 * @author Administartor
 *
 */
public class SimpleSelector implements Selector {

	private String select;
	private String from;
	private String join;
	private String where;
	private String orderby;
	private String groupby;
	private String limit;
	private Conditionor condition;
	
	private List<Selector> inlines;
	private Object[] params;
	private int[] types;
	
	public String toString(){
		return DaoUtil.concatStrings(select,from,join,where,orderby,groupby,limit);
	}
		
	public Selector select(String... colmuns) {
		// TODO Auto-generated method stub
		select = "select ";
		for(int i = 0; i < colmuns.length; i++){
			select = select 
					+ (i == colmuns.length-1?colmuns[i]:colmuns[i]+",");
		}
		return this;
	}

	public Selector from(String... tables) {
		// TODO Auto-generated method stub
		if(tables.length == 0) throw new RuntimeException("One table name is necessary");
		if(from == null) from = " from " + tables[0];
		else from = from + ", " + tables[0];
		for(int i = 1; i < tables.length; i++){
			from = from 
					+ "," + tables[i];
		}
		return this;
	}
	
	public Selector from(Selector selector,String as) {
		// TODO Auto-generated method stub
		if(inlines == null) inlines = new ArrayList<Selector>();
		inlines.add(selector);
		if(from == null) from = " from (" + selector.toString() + ") " + as;
		else from = from + ", (" + selector.toString() + ") " + as;
		return this;
	}
	

	public Selector where(Conditionor condition) {
		// TODO Auto-generated method stub
		if(condition == null) return this;
		this.condition = condition;
		where = " where " + condition;
		return this;
	}

	public Selector orderby(String... colmuns) {
		// TODO Auto-generated method stub
		orderby = " order by ";
		for(int i = 0; i < colmuns.length; i++){
			orderby = orderby 
					+ (i == colmuns.length-1?colmuns[i]:colmuns[i]+",");
		}
		return this;
	}

	public Selector groupby(String... colmuns) {
		// TODO Auto-generated method stub
		groupby = " group by ";
		for(int i = 0; i < colmuns.length; i++){
			groupby = groupby 
					+ (i == colmuns.length-1?colmuns[i]:colmuns[i]+",");
		}
		return this;
	}

	public Selector join(String table, String left, String op, String right) {
		// TODO Auto-generated method stub
		join = " join " + table + " on " + left + " " + op + " " + right;
		return this;
	}

	public Selector limit(int length) {
		// TODO Auto-generated method stub
		limit = " limit " + length + " ";
		return this;
	}

	public Selector limit(int index, int length) {
		// TODO Auto-generated method stub
		limit = " limit " + index + "," + length + " ";
		return this;
	}
	
	public Object[] getParams() {
		// TODO Auto-generated method stub
		if(inlines == null && condition == null) return  null;
		if(params == null){
			params = new Object[0];
			types = new int[0];
		}else{
			return params;
		}
		if(inlines != null) {
			for(Selector sel : inlines){
				params = DaoUtil.concat(params, sel.getParams());
				types = DaoUtil.concat(types, sel.getTypes());
			}
		}
		if(condition != null){
			params = DaoUtil.concat(params,condition.getParams());
			types = DaoUtil.concat(types, condition.getIntTypes());
		}

		return params;
	}

	public int[] getTypes() {
		// TODO Auto-generated method stub
		if(inlines == null && condition == null) return  null;
		if(types == null){
			getParams();
			return types;
		}else{
			return types;
		}
	}

}
