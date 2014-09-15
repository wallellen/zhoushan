/**
 * 
 */
package com.jtang.service.impl;

import java.sql.Types;
import java.util.List;

import com.jtang.dao.BasicManagerDao;
import com.jtang.dao.sqlUtil.Zql;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.Storage;
import com.jtang.service.IStorageService;

/**
 * @author Administartor
 *
 */
public class StorageServiceImpl implements IStorageService {

	public BasicManagerDao manager;
	
	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}


	/* (non-Javadoc)
	 * @see com.jtang.service.IStorageService#getStorageListByUser(java.lang.String)
	 */
	public List<Storage> getStorageListByIds(String [] storageId,String enterpriseId) {
		// TODO Auto-generated method stub
		if(storageId == null) return null;
		String sql = "select * from storage where id in ("; 
		
		for(int i=0;i<storageId.length;i++){
			sql=sql+storageId[i];
			if(i!=storageId.length-1){
				sql+=",";
			}
		}
		
		sql+=")";
		
		sql += " and enterpriseId = ?";
		
		Object [] params ={enterpriseId};
		int [] argTypes = {Types.VARCHAR};
		
		return manager.query(sql, params,argTypes,RowMapperEnum.STORAGE.getMapperName());
	}
	

	public int addStorageUse(int storageId, int count) {
		// TODO Auto-generated method stub
		String sql = "UPDATE storage SET used=used+? WHERE id=?";
		Object[] paras = {count,storageId};
		int[] types = {Types.INTEGER,Types.INTEGER};
		return manager.update(sql, paras, types);
	}

	public int minusStorageUse(int storageId,  int count) {
		// TODO Auto-generated method stub
		String sql = "UPDATE storage SET used=used-? WHERE id=?";
		Object[] paras = {count,storageId};
		int[] types = {Types.INTEGER,Types.INTEGER};
		return manager.update(sql, paras, types);
	}

	public int updateStorageUse(int storageId) {
		// TODO Auto-generated method stub
		String sql = "UPDATE storage SET used = (SELECT SUM(pro.proCount) FROM pro WHERE pro.storageId=? ) WHERE storage.id=?";
		Object[] paras = {storageId,storageId};
		int[] types = {Types.INTEGER,Types.INTEGER};
		return manager.update(sql, paras, types);
	}


	public boolean isStorageExists(int storageId) {
		// TODO Auto-generated method stub
		String sql = "select * from storage where id=?";
		Object[] args={storageId};
		int [] types = {Types.INTEGER};
		List<Storage> storage = manager.query(sql, args,types,RowMapperEnum.STORAGE.getMapperName());
		if(storage.size() == 1){
			return true;
		}else{
			return false;
		}
		
	}

	public int updateStorage(Storage storage) {
		// TODO Auto-generated method stub
		String sql = "update storage set length=?,width=?,high=?,capacity=?,name=?,used=?,maxTemp=?,minTemp=? where id = ?";
		Object [] args = {storage.getLength(),storage.getWidth(),storage.getHigh(),
						 storage.getCapacity(),storage.getName(),storage.getUsed(),storage.getMaxTemp(),storage.getMinTemp(),
						 storage.getId()};
		int [] types = {Types.INTEGER,Types.INTEGER,Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.INTEGER,
						Types.FLOAT,Types.FLOAT,Types.INTEGER};
		return manager.update(sql, args, types);
	}

	public int updateMaxMinTemp(int storageId, float maxTemp, float minTemp) {
		// TODO Auto-generated method stub
		String sql = "update storage set maxTemp=?,minTemp=? where id=?";
		Object[] args = {maxTemp,minTemp,storageId};
		int [] types = {Types.FLOAT,Types.FLOAT,Types.INTEGER};
		
		return manager.update(sql, args, types);
	}

	public Storage getStorageById(int storageId) {
		// TODO Auto-generated method stub
		List<Storage> ss = manager.query(Zql.selectFrom("storage")
				.where(Zql.condition("id", "=", storageId)), RowMapperEnum.STORAGE.getMapperName());
		return ss.get(0);
	}

	@Override
	public List<Storage> getStorageListByEnterId(String enterpriseId) {
		// TODO Auto-generated method stub
		return manager.query("SELECT * FROM storage WHERE enterpriseId = '" + enterpriseId+"'",
				null, null, 
				RowMapperEnum.STORAGE.getMapperName());
	}

}
