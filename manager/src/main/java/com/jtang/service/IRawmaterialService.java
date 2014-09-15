package com.jtang.service;

import java.util.List;

import com.jtang.dao.sqlUtil.Conditionor;
import com.jtang.model.PageInfo;
import com.jtang.model.Rawmaterial;

public interface IRawmaterialService {
	/**
	 * 获取数据库中所有原材料
	 * 如不指明企业id,则获取全部原料料
	 * @return
	 */
	public List<Rawmaterial > getAllRawmaterials(String... enterpriseId);
	
	/**
	 * 获取数据库中有余量的原材料
	 * 若不指明企业id,则获取全部原材料
	 * @return
	 */
	public List<Rawmaterial > getUnusedRawmaterials(String... enterpriseId);
	
	/**
	 * 向数据库中添加一种原材料
	 * 成功则返回1，失败则返回0
	 */
	public int addARawmaterial(Rawmaterial rawmaterial);
	
	/**
	 * 根据主键修改所有的原材料信息
	 * 修改原材料信息
	 */
	public int updateARawmaterial(Rawmaterial rawmaterial);
	
	/**
	 * 删除一个指定的Rawmaterial
	 */
	public int deleteARawmaterial(String rawId);
	
	
	/**
	 * 当加工原料时调用该方法消耗原料
	 * @param rawId
	 * @param minus
	 * @return
	 */
	public int minusRawCount(String rawId,int minus);
	
	
	/**
	 * 分页查询所需数据
	 * @param pageInfo
	 * @param enterpriseId
	 * @return
	 */
	public  PageInfo<Rawmaterial> getUnuesedRawmaterialsByPage(PageInfo<Rawmaterial> pageInfo,String enterpriseId);
	public  PageInfo<Rawmaterial> getUnuesedRawmaterialsByOrder(PageInfo<Rawmaterial> pageInfo,String enterpriseId,String order);
	public  PageInfo<Rawmaterial> getUnuesedRawmaterialsByCon(PageInfo<Rawmaterial> pageInfo,Conditionor con);
	
	public Rawmaterial getRawmaterialById(String rawId);
	
	public List<Rawmaterial> getRawsByIds(List<String> rawIds);
}
