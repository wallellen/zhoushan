package com.jtang.service.impl;

import java.sql.Types;
import java.util.List;

import com.jtang.dao.BasicManagerDao;
import com.jtang.dao.DaoUtil;
import com.jtang.dao.sqlUtil.Conditionor;
import com.jtang.dao.sqlUtil.Selector;
import com.jtang.dao.sqlUtil.Zql;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.PageInfo;
import com.jtang.model.Rawmaterial;
import com.jtang.service.IRawmaterialService;

/**
 * @author chenminglong
 *
 */
public class RawmaterialServiceImpl implements IRawmaterialService {
	
    private BasicManagerDao manager;  //由Spring注入实体类

	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}

	public List<Rawmaterial> getAllRawmaterials(String... enterpriseId) {
		// TODO Auto-generated method stub
		if(enterpriseId !=null && enterpriseId.length > 1) throw new RuntimeException("just need one enterprise id");
		Selector sel = Zql.select("*")
				.from("rawmaterial");
		if(enterpriseId != null && enterpriseId.length == 1)
			sel.where(Zql.condition("enterpriseId", "=", enterpriseId[0]));
		return manager.query(sel, RowMapperEnum.RAWMATERIAL.getMapperName());
	}

	public int addARawmaterial(Rawmaterial rawmaterial) {
		// TODO Auto-generated method stub
		return manager.addAuto(rawmaterial);
	}

	public int updateARawmaterial(Rawmaterial rawmaterial) {
		// TODO Auto-generated method stub
		return manager.updateAuto(rawmaterial);
	}

	public int deleteARawmaterial(String rawId) {
		// TODO Auto-generated method stub
		return manager.delete(Zql.deleteFrom("rawmaterial")
				.where(Zql.condition("rawId", "=", rawId)));
	}
	

	public List<Rawmaterial> getUnusedRawmaterials(String... enterpriseId) {
		// TODO Auto-generated method stub
		if(enterpriseId !=null && enterpriseId.length > 1) throw new RuntimeException("just need one enterprise id");
		Selector sel = Zql.select("*")
				 .from("rawmaterial");
		Conditionor con = Zql.condition("spare", ">", 0);
		if(enterpriseId !=null && enterpriseId.length == 1)
			con.and(Zql.condition("enterpriseId", "=", enterpriseId[0]));
		
		return manager.query(sel.where(con), RowMapperEnum.RAWMATERIAL.getMapperName());
	}

	public int minusRawCount(String rawId, int minus) {
		// TODO Auto-generated method stub
		return manager.update(Zql.update("rawmaterial")
				                .set("spare", "--", minus)
				                .where(Zql.condition("rawId", "=", rawId)));
	}
	
	public  PageInfo<Rawmaterial> getUnuesedRawmaterialsByPage(PageInfo<Rawmaterial> pageInfo,String enterpriseId) {
		Conditionor con = Zql.condition("spare", ">", 0);
		con.and(Zql.condition("enterpriseId", "=", enterpriseId));
		List<Rawmaterial> rawList = manager.query(DaoUtil.getSelectorByPage(pageInfo, "rawmaterial", con,manager), RowMapperEnum.RAWMATERIAL.getMapperName());
		pageInfo.setDataList(rawList);
		return pageInfo;
	}
	
	public  PageInfo<Rawmaterial> getUnuesedRawmaterialsByCon(PageInfo<Rawmaterial> pageInfo,Conditionor con){
		con = con == null ? Zql.condition("spare", ">", 0) : con.and(Zql.condition("spare", ">", 0));
		List<Rawmaterial> rawList = manager.query(DaoUtil.getSelectorByPage(pageInfo, "rawmaterial", con,manager), RowMapperEnum.RAWMATERIAL.getMapperName());
		pageInfo.setDataList(rawList);
		return pageInfo;
	}
	

	public Rawmaterial getRawmaterialById(String rawId) {
		// TODO Auto-generated method stub
		Selector sel = Zql.select("*")
				 .from("rawmaterial")
				 .where(Zql.condition("rawId", "=", rawId));
		List<Rawmaterial> rawList = manager.query(sel, RowMapperEnum.RAWMATERIAL.getMapperName());
		return rawList == null?null:rawList.get(0);
	}
	
	public List<Rawmaterial> getRawsByIds(List<String> rawIds){
		Selector sel = Zql.select("*")
				.from("rawmaterial")
				.where(Zql.condition("rawId", "in", rawIds));
		return manager.query(sel, RowMapperEnum.RAWMATERIAL.getMapperName());
	}

	@Override
	public PageInfo<Rawmaterial> getUnuesedRawmaterialsByOrder(
			PageInfo<Rawmaterial> pageInfo, String enterpriseId, String order) {
		// TODO Auto-generated method stub
		Conditionor con = Zql.condition("spare", ">", 0);
		con.and(Zql.condition("enterpriseId", "=", enterpriseId));
		List<Rawmaterial> rawList = manager.query(DaoUtil.getSelectorByPage(pageInfo, "rawmaterial", con,manager).orderby(order), RowMapperEnum.RAWMATERIAL.getMapperName());
		pageInfo.setDataList(rawList);
		return pageInfo;
	}
}
