/**
 * 
 */
package com.jtang.service;

import java.util.List;

import com.jtang.model.Storage;

/**
 * @author Administartor
 *
 */
public interface IStorageService {
	/**
	 * 返回某企业下所有的仓库
	 * @param enterpriseId
	 * @return List<Storage>
	 */
	public List<Storage> getStorageListByEnterId(String enterpriseId);
	/**
	 * 通过UserId返回该User掌管的所有仓库列表
	 * @param userId
	 * @return
	 */
	public List<Storage> getStorageListByIds(String [] storageId,String enterpriseId);
	
	/**
	 * 增加库存使用
	 * @param storageId
	 * @param enterpriseId
	 * @param count
	 */
	public int addStorageUse(int storageId,int count);
	/**
	 * 减少库存使用
	 * @param storageId
	 * @param enterpriseId
	 * @param count
	 */
	public int minusStorageUse(int storageId,int count);
	/**
	 * 依赖商品表，自动更新各仓库库存使用量
	 *   应该要进行连表操作，不需要指定仓库id
	 * @param storageId
	 * @param enterpriseId
	 */
	public int updateStorageUse(int storageId);
	
	
	/**
	 * 通过storageId判断指定的仓库师傅存在
	 */
	public boolean isStorageExists(int storageId);
	
	/**
	 * 修改仓库参数
	 * @param storage
	 * @return
	 */
	public int updateStorage(Storage storage);
	
	/**
	 * 
	 * @param storageId
	 * @param maxTemp
	 * @param minTemp
	 * @return
	 */
	public int updateMaxMinTemp(int storageId,float maxTemp,float minTemp);
	
	public Storage getStorageById(int storageId);
	
}
