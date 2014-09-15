package com.jtang.service;

import java.util.List;
import com.jtang.model.ProductQuery;

public interface IProductQueryService {
	/**
	 * 获取数据库中所有产品
	 * @return
	 */
	public List<ProductQuery > getAllProductQuerys();
	
	/**
	 * 获取starttIndex开始的pageSize条产品
	 * @return
	 */
	public List<ProductQuery> getProductQueryPage(int startIndex,int pageSize);
	
	/**
	 * 获取starttIndex开始的pageSize条产品而且barcode确定
	 * @return
	 */
	public List<ProductQuery> getProductQueryPage(int startIndex,int pageSize,String barcode);
	
	
}
