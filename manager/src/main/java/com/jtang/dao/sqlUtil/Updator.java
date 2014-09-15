package com.jtang.dao.sqlUtil;

public interface Updator {
	public Updator update(String table);
	/**
	 * op支持+,-,++,--
	 * ++表示 set colmun = colmun + obj
	 * --类同
	 * @param colmun
	 * @param op
	 * @param obj
	 * @return
	 */
	public Updator set(String colmun,String op,Object obj);

	public Updator where(Conditionor condition);

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
