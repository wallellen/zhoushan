package com.jtang.dao.sqlUtil;

public interface Deletor {
	public Deletor deleteFrom(String table);
	public Deletor where(Conditionor condition);
	
	/**
	 * 返回set部分和where条件中的问号对应的object
	 * @return
	 */
	public Object[] getParams();
	
	/**
	 * 返回参数对应的类型
	 * @return
	 */
	public int[] getTypes();
	
	public String toString();

}
