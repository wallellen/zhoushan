package com.jtang.dao.sqlUtil;


public interface Conditionor {
	public Conditionor and(Conditionor next);
	public Conditionor or(Conditionor next);
		
	/**
	 * 按连接顺序根据逻辑词串联condition
	 * @return
	 */
	public String toString();
	
	/**
	 * 返回问号对应的object
	 * @return
	 */
	public Object getContent();
	
	/**
	 * 返回嵌套的下一个condition
	 * @return
	 */
	public Conditionor next();
	
	/**
	 * 返回where条件中的问号对应的object
	 * @return
	 */
	public Object[] getParams();
	/**
	 * 返回参数对应的类型
	 * @return
	 */
	public int[] getIntTypes();
	
	public Integer[] getTypes();
	

	/**
	 * @param next the next to set
	 */
	public void setNext(Conditionor next);
	
	public void setLogic(String logic);
	
	/**
	 * 将该Conditionor用括号括起来
	 * @return
	 */
	public Conditionor brackets();
	
	/**
	 * 永远不应该主动调用该方法.
	 */
	public void bracketsOver();
	
}
