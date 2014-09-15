package com.jtang.service.impl;

import java.sql.Types;
import java.util.List;

import com.jtang.dao.BasicManagerDao;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.ProductQuery;
import com.jtang.service.IProductQueryService;

/**
 * @author chenminglong
 *
 */
public class ProductQueryServiceImpl implements IProductQueryService {
	
    private BasicManagerDao manager;  //由Spring注入实体类

	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}

	public List<ProductQuery> getAllProductQuerys() {
		// TODO Auto-generated method stub
		String sql = "SELECT p.Id,p.Name p_name,p.Barcode,p.ProductionDate p_pdate," +
				"s.name s_name,sr.StartTime s_stime,sr.endtime s_etime,sr.temperature s_temp," +
				"tr.startplace,tr.endplace,tr.starttime t_stime,tr.endtime t_etime,tr.temperature t_temp," +
				"rm.name rm_name,rm.manufacturer,rm.productionplace,rm.productiondate rm_pdate,recorder.name recorderName " +
				"FROM product p,storage_record sr,storage s,transportation_record tr,rawmaterial rm,recorder " +
				"WHERE p.StorageRecordId=sr.Id AND sr.StorageId=s.Id AND p.TransportationRecordId=tr.Id " +
				"AND p.RawmaterialRecordId=rm.Id AND p.RecorderId=recorder.Id;";
		List<ProductQuery> AllProductQuerys = manager.query(sql, null,null,RowMapperEnum.PRODUCTQUERY.getMapperName());
		return AllProductQuerys;
	}
	
	public List<ProductQuery> getProductQueryPage(int startIndex,int pageSize){
		// TODO Auto-generated method stub
		String sql = "SELECT p.Id,p.Name p_name,p.Barcode,p.ProductionDate p_pdate," +
				"s.name s_name,sr.StartTime s_stime,sr.endtime s_etime,sr.temperature s_temp," +
				"tr.startplace,tr.endplace,tr.starttime t_stime,tr.endtime t_etime,tr.temperature t_temp," +
				"rm.name rm_name,rm.manufacturer,rm.productionplace,rm.productiondate rm_pdate,recorder.name recorderName " +
				"FROM product p,storage_record sr,storage s,transportation_record tr,rawmaterial rm,recorder " +
				"WHERE p.StorageRecordId=sr.Id AND sr.StorageId=s.Id AND p.TransportationRecordId=tr.Id " +
				"AND p.RawmaterialRecordId=rm.Id AND p.RecorderId=recorder.Id LIMIT ?,?";
		Object[] args = {startIndex,pageSize};
		int[] argTypes = {Types.INTEGER,Types.INTEGER};
		List<ProductQuery> AllProductQuerys = manager.query(sql, args ,argTypes , RowMapperEnum.PRODUCTQUERY.getMapperName());
		return AllProductQuerys;
	}
	
	public List<ProductQuery> getProductQueryPage(int startIndex,int pageSize,String barcode){
		// TODO Auto-generated method stub
		String sql = "SELECT p.Id,p.Name p_name,p.Barcode,p.ProductionDate p_pdate," +
				"s.name s_name,sr.StartTime s_stime,sr.endtime s_etime,sr.temperature s_temp," +
				"tr.startplace,tr.endplace,tr.starttime t_stime,tr.endtime t_etime,tr.temperature t_temp," +
				"rm.name rm_name,rm.manufacturer,rm.productionplace,rm.productiondate rm_pdate,recorder.name recorderName " +
				"FROM product p,storage_record sr,storage s,transportation_record tr,rawmaterial rm,recorder " +
				"WHERE p.StorageRecordId=sr.Id AND sr.StorageId=s.Id AND p.TransportationRecordId=tr.Id " +
				"AND p.RawmaterialRecordId=rm.Id AND p.RecorderId=recorder.Id AND p.Barcode=? LIMIT ?,?";
		Object[] args = {barcode,startIndex,pageSize};
		int[] argTypes = {Types.VARCHAR,Types.INTEGER,Types.INTEGER};
		//List<ProductQuery> AllProductQuerys = manager.query(sql, );
		return manager.query(sql, args ,argTypes , RowMapperEnum.PRODUCTQUERY.getMapperName());
	}

}
