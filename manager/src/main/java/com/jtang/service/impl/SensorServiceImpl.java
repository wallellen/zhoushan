/**
 * 
 */
package com.jtang.service.impl;

import java.sql.Types;
import java.util.List;

import com.jtang.dao.BasicManagerDao;
import com.jtang.dao.DaoUtil;
import com.jtang.dao.sqlUtil.Conditionor;
import com.jtang.dao.sqlUtil.Zql;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.PageInfo;
import com.jtang.model.Sensor;
import com.jtang.service.ISensorService;

/**
 * @author zhouxinyu
 *
 */
public class SensorServiceImpl implements ISensorService {

	public BasicManagerDao manager;  //由Spring注入实体类

	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}

	/* (non-Javadoc)
	 * @see com.jtang.service.ISensorService#getAllSensors()
	 */
	public List<Sensor> getAllSensors() {
		// TODO Auto-generated method stub
		String sql = "select * from sensor";
		List<Sensor> allSensors = manager.query(sql, null,null,RowMapperEnum.SENSOR.getMapperName());
		return allSensors;
	}

	public int addASensor(Sensor sensor) {
		// TODO Auto-generated method stub
//		String sql = "insert into sensor(extAddr,shortAddr,nodeTypes,workStatus,fatherNode,position,creator"
//				+ ",createTime,mender,mendTime,workTime,storageId,name) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
//		Object[] args = {sensor.getExtAddr(),sensor.getShortAddr(),sensor.getNodeTypes(),sensor.getWorkStatus()
//				,sensor.getFatherNode(),sensor.getPosition(),sensor.getCreator(),
//				sensor.getCreateTime(),sensor.getMender(),sensor.getMendTime(),sensor.getWorkTime(),
//				sensor.getStorageId(),sensor.getName()};
//		int [] argTypes = {Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER,Types.VARCHAR,
//				Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER,Types.VARCHAR};
//		return manager.add(sql, args, argTypes);
		return manager.addAuto(sensor);		
	}
	
	public boolean isSensorExists(String extAddr) {
		// TODO Auto-generated method stub
		String sql = "select * from sensor where extAddr=?";
		Object[] args={extAddr};
		int [] types = {Types.VARCHAR};
		List<Sensor> sensors = manager.query(sql, args,types,RowMapperEnum.SENSOR.getMapperName());
		if(sensors.size() == 1){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * 该方法不能用于更新position
	 * 该方法主要交给传感器网络来调用
	 */
	public int updateASensor(Sensor sensor) {
		// TODO Auto-generated method stub
//		String sql = "update sensor set shortAddr = ?,nodeTypes = ?,workStatus = ?,fatherNode=?"
//				+ ",mender=?,mendTime=?,storageId=? WHERE extAddr = ?";
//		Object[] args = {sensor.getShortAddr(),sensor.getNodeTypes(),sensor.getWorkStatus()
//				,sensor.getFatherNode(),sensor.getMender(),sensor.getMendTime(),sensor.getStorageId(),sensor.getExtAddr()};
//		int [] argTypes = {Types.VARCHAR,Types.INTEGER,Types.INTEGER,Types.VARCHAR,
//				Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR};
//		
//		return manager.update(sql, args, argTypes);
		return manager.updateAuto(sensor,"position","creator","createTime","workTime","name");
	}

	public int deleteASensor(String extAddr) {
		// TODO Auto-generated method stub
		String sql = "delete from sensor where extAddr=?";
		Object[] args = {extAddr};
		int [] argTypes = {Types.VARCHAR};
		return manager.delete(sql, args,argTypes);
		
		
	}

	/**
	 * Client 调用此方法更新sensor，但不能更新创建者，创建时间以及工作时间
	 */
	public int updateSensorForClient(Sensor sensor) {
		// TODO Auto-generated method stub
//		String sql = "update sensor set shortAddr = ?,nodeTypes = ?,workStatus = ?,fatherNode=?"
//				+ ",mender=?,mendTime=?,position=?,name=?,storageId=? WHERE extAddr = ?";
//		Object[] args = {sensor.getShortAddr(),sensor.getNodeTypes(),sensor.getWorkStatus()
//				,sensor.getFatherNode(),sensor.getMender(),sensor.getMendTime(),sensor.getPosition()
//				,sensor.getName(),sensor.getStorageId(),sensor.getExtAddr()};
//		int [] argTypes = {Types.VARCHAR,Types.INTEGER,Types.INTEGER,Types.VARCHAR,
//				Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR};
//		
//		return manager.update(sql, args, argTypes);
		return manager.updateAuto(sensor, "creator","createTime","workTime");
	}

	public int resetSensorForClient(String extAddr) {
		// TODO Auto-generated method stub
		String sql = "update sensor set worktime = ? where extaddr = ?";
		Object [] args  = {0,extAddr};
		int [] argTypes = {Types.INTEGER,Types.VARCHAR};
		
		return manager.update(sql, args, argTypes);
	}

	public int updateWorkTime(String extAddr, int timePlus) {
		// TODO Auto-generated method stub
		String sql = "update sensor set worktime = worktime + ? where extaddr = ?";
		Object [] args  = {timePlus,extAddr};
		int [] argTypes = {Types.INTEGER,Types.VARCHAR};
		
		return manager.update(sql, args, argTypes);
	}

	public int updateWorkStatus(String extAddr, int workStatus) {
		// TODO Auto-generated method stub
		String sql = "update sensor set workstatus =  ? where extaddr = ?";
		Object [] args  = {workStatus,extAddr};
		int [] argTypes = {Types.INTEGER,Types.VARCHAR};
		
		return manager.update(sql, args, argTypes);
	}

	public List<Sensor> getSensorListByStorageId(int storageId) {
		// TODO Auto-generated method stub
		String sql = "select * from sensor where storageId = ?";
		Object[] paras ={storageId};
		int[] types = {Types.INTEGER};
		List<Sensor> allSensors = manager.query(sql, paras,types,RowMapperEnum.SENSOR.getMapperName());
		return allSensors;
	}

	@Override
	public PageInfo<Sensor> getSensorListByPage(PageInfo<Sensor> pageInfo,
			Conditionor con, int storageId) {
		// TODO Auto-generated method stub
		Conditionor newCon = Zql.condition("storageId", "=", storageId)
				.and((con == null?null:con.brackets()));
		List<Sensor> dataList = manager.query(DaoUtil.getSelectorByPage(pageInfo, "sensor", newCon, manager),
				RowMapperEnum.SENSOR.getMapperName());
		pageInfo.setDataList(dataList);
		return pageInfo;
	}
	
	

}
