package com.jtang.dao.sqlUtil;

public interface Selector {
	public Selector select(String... colmuns);
	/**
	 * 从哪些表查询?
	 * @param tables
	 * @return
	 */
	public Selector from(String... tables);
	
	/**
	 * 
	 * 从select结果中查询?
	 * 不支持变长参数,需从多个select结果中查询,请调用多次
	 * @param selector
	 * @return
	 */
	public Selector from(Selector selector,String as);

	
	public Selector where(Conditionor condition);
	
	/**
	 * 
	 * @param colmuns
	 * @return
	 * 默认升序，如需降序请指明,eg:orderby("id desc","name");
	 */
	public Selector orderby(String... colmuns);
	
	public Selector groupby(String... colmuns);
	
	/**
	 * From City JOIN Person ON City.Id = Person.cityId等价于：
	 * from("City").join("Person","City.Id","=","Person.cityId");
	 * @param table
	 * @param left
	 * @param op
	 * @param right
	 * @return
	 */
	public Selector join(String table,String left,String op,String right);
	
	public Selector limit(int length);
	
	public Selector limit(int index,int length);
	/**
	 * 返回where条件中的问号对应的object
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
