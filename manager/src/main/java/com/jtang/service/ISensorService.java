package com.jtang.service;

import java.util.List;

import com.jtang.dao.sqlUtil.Conditionor;
import com.jtang.model.PageInfo;
import com.jtang.model.Sensor;

public interface ISensorService {
	/**
	 * 获取数据库中所有传感器
	 * @return
	 */
	public List<Sensor > getAllSensors();
	
	/**
	 * 根据指定仓库ID获取该仓库的所有传感器
	 * @param storageId
	 * @return
	 */
	public List<Sensor> getSensorListByStorageId(int storageId);
	
	/**
	 * 向数据库中添加一个传感器
	 * 成功则返回1，失败则返回0
	 */
	public int addASensor(Sensor sensor);
	
	/**
	 * 检查某个Sensor是否存在数据库中,是则返回1,不是则返回0
	 */
	public boolean isSensorExists(String extAddr);
	
	/**
	 * 更新sensor,该方法只能给zigbee网络调用
	 */
	public int updateASensor(Sensor sensor);
	
	/**
	 * 删除一个指定Mac地址的Sensor
	 */
	public int deleteASensor(String extAddr);
	
	/**
	 * 更新Sensor,由web端调用
	 */
	public int updateSensorForClient(Sensor sensor);
	
	/**
	 * Reset sensor,重置传感器的工作时间,方便统计电量
	 */
	public int resetSensorForClient(String extAddr);
	
	/**
	 * 更新sensor的工作时间
	 */
	public int updateWorkTime(String extAddr,int timePlus);
	
	/**
	 * 更新传感器的工作状态
	 */
	public int updateWorkStatus(String extAddr,int workStatus);
	
	public PageInfo<Sensor> getSensorListByPage(PageInfo<Sensor> pageInfo, Conditionor con , int storageId);
	

}
