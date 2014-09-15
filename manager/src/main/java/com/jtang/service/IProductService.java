package com.jtang.service;

import java.util.List;
import com.jtang.model.Pro_Trace;

public interface IProductService {
	/**
	 * 获取数据库中所有产品
	 * @return
	 */
	public List<Pro_Trace > getAllProducts();
	
	/**
	 * 向数据库中添加一种产品
	 * 成功则返回1，失败则返回0
	 */
	public int addAProduct(Pro_Trace product);
	
	/**
	 * 修改产品信息
	 */
	public int updateAProduct(Pro_Trace product);
	
	/**
	 * 删除一个指定Mac地址的Sensor
	 */
	public int deleteAProduct(String extAddr);
	
	/**
	 * 查询产品记录数
	 */
	public int getProductsNum();
	
	
}
