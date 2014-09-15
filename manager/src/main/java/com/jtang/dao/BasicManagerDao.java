package com.jtang.dao;

import java.util.List;
import java.util.Map;

import com.jtang.dao.sqlUtil.Deletor;
import com.jtang.dao.sqlUtil.Insertor;
import com.jtang.dao.sqlUtil.Selector;
import com.jtang.dao.sqlUtil.Updator;

/**
 * basic Dao 
 * @author yyj
 *
 */
public interface BasicManagerDao {
	
    /**
     * sql 
     * @param sql
     * @param args
     * @param types
     * @param mapperClassName
     *        对应的表mapper名  , 详细在RowMapperEnum
     *         @see RowMapperEnum
     * @return
     *     
     */
	public <T> List<T> query(String sql,Object[] args, int[]types , String mapperClassName);
	
	/**
	 * 
	 * @param sel Selector对象包含查询的sql语句，参数表等信息
	 * @param mapperClassName 对应的表mapper名  , 详细在RowMapperEnum
	 * @see Selector
	 * @see RowMapperEnum
	 * @return
	 */
	public <T> List<T> query(Selector sel, String mapperClassName);
	/**
	 *  query for int
	 * @param sql
	 * @param args
	 * @param types
	 * @return
	 */
	public int queryInt(String sql,Object[] args,int types[]);
	
	public int queryInt(Selector sel);
	
	/**
	 *  update
	 * @param sql
	 * @param args
	 * @param argTypes
	 * @return
	 */
	public int update(String sql,Object[] args,int [] argTypes);
	
	/**
	 * 一个Updator里面包含了update语句，参数，参数类型等信息
	 * @param up
	 * @return
	 */
	public int update(Updator up);
	
    /**
     * query , return as map 
     * @param sql
     * @param args
     * @param types
     * @return
     */
	public List<Map<String,Object>> queryForListMap(String sql ,Object[] args,int[] types);
	
	/**
	 * 单列查询
	 * @param sql
	 * @param args
	 * @param type 返回查询列的数据类型
	 * @return
	 */
	public <T> List<T> queryForList( String sql , Object[] args , Class<T> type);
	
	public List<Map<String,Object>> queryForList(Selector sel);
	
	/**
	 * add 
	 * @param sql
	 * @param args
	 * @param argTypes
	 * @return
	 *      1  success
	 *      0  fail
	 */
	public int add(String sql,Object[] args,int[] argTypes);
	
	public int add(Insertor in);
	
	/**
	 * delete
	 * @param sql
	 * @param args
	 * @param argTypes
	 * @return
	 */
	public int delete(String sql,Object[] args,int [] argTypes);
	
	public int delete(Deletor del);
	
	/**
	 * 将一个bean插入到数据库，特别适用于bean中大部分属性都需要插入到数据库时。
	 * 从需求来看，所有的Insert使用该方法都是合适的
	 * @param obj This obj will insert to the db
	 * @param args some variable you do't want to insert to db.
	 * Note:1.args参数应该是你不想反映到数据库中的bean字段,而不是表的字段；
	 * 
	 * @return the number of rows affected
	 */
	public int addAuto(Object obj,String... args) ;
	
	/**
	 * 将指定的obj修改到db中，根据obj中主键注解所在来修改。
	 * 即该方法只能根据主键来修改obj，不支持其他功能
	 * 当只需要update极个别字段或者需要其他高级功能时，请使用Zql
	 * @param obj
	 * @param args args参数应该是你不想反映到数据库中的bean字段,而不是表的字段，将你不想修改到数据库中的字段指明出来
	 * @return the number of rows affected
	 * @see Zql
	 */
	public int updateAuto(Object obj,String...args );
}
