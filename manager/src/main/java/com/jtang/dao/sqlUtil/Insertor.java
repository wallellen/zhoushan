package com.jtang.dao.sqlUtil;

public interface Insertor {
	public Insertor insertInto(String table);
	public Insertor keyValue(String key,Object value);
	
	/**
	 * 返回insert中的问号对应的object
	 * @return
	 */
	public Object[] getParams();
	
	/**
	 * 返回参数对应的类型
	 * @return
	 */
	public int[] getTypes();
	
}
