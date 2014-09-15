package com.jtang.service.impl;

import java.sql.Types;
import java.util.Date;
import java.util.List;

import com.jtang.dao.BasicManagerDao;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.Pro_Trace;
import com.jtang.service.IProductService;

/**
 * @author chenminglong
 *
 */
public class ProductServiceImpl implements IProductService {
	
    private BasicManagerDao manager;  //由Spring注入实体类

    
	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}

	public List<Pro_Trace> getAllProducts() {
		// TODO Auto-generated method stub
		String sql = "select * from product";
		List<Pro_Trace> AllProducts = manager.query(sql, null , null , RowMapperEnum.PRODUCTTRACE.getMapperName());
		return AllProducts;
	}
	
	public int getProductsNum() {
		// TODO Auto-generated method stub
		String sql = "select count(*) from product";
		return manager.queryInt(sql, null, null);
	}

	public int addAProduct(Pro_Trace product) {
		// TODO Auto-generated method stub
		String sql="INSERT INTO product(Name,Barcode,ProductionDate,StorageRecordId,"
				+ "TransportationRecordId, RawmaterialRecordId, RecorderId, RecordDate)"
				+ "VALUES(?, ?, ?, ?, ?, ?, '1', ?)";
		/*
		Object[] args = {product.getName(),product.getBarcode(),product.getProductionDate(),product.getStorageRecordId()
				,product.getTransportationRecordId(),product.getRawmaterialRecordId(),product.getRecorderId(),
				product.getRecordDate()};
		int [] argTypes = {Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER,Types.VARCHAR,
				Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
				*/
		Object[] args = {product.getName(),product.getBarcode(),product.getProductionDate(),product.getStorageRecordId(),
				product.getTransportationRecordId(),product.getRawmaterialRecordId(),new Date()};
		int [] argTypes = {Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP,Types.INTEGER,Types.INTEGER,Types.INTEGER,Types.TIMESTAMP};
		return manager.add(sql, args, argTypes);
	}

	public int updateAProduct(Pro_Trace product) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int deleteAProduct(String extAddr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
