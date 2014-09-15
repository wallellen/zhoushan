/**
 * 
 */
package com.jtang.service.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.jtang.dao.BasicManagerDao;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.Temperature;
import com.jtang.service.ITempService;

/**
 * @author Administartor
 *
 */
public class TempServiceImpl implements ITempService {
	
	public BasicManagerDao manager;
	
	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}

	/* (non-Javadoc)
	 * @see com.jtang.service.ITempService#getDataByTime(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<Temperature> getDataByTime(String extAddr, String startTime,
			String endTime) {
		// TODO Auto-generated method stub
		String sql = "select * from temperature where sensorExtAddr = ? and (recordTime between ? and ?) order by recordTime";
		Object [] args = {extAddr,startTime,endTime};
		int [] argTypes = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		return manager.query(sql, args,argTypes,RowMapperEnum.TEMP.getMapperName());
	}

	/* (non-Javadoc)
	 * @see com.jtang.service.ITempService#insertRecord(com.jtang.model.Temperature)
	 */
	public int insertRecord(Temperature temp) {
		// TODO Auto-generated method stub
		String sql = "insert into temperature(sensorExtAddr,recordTime,temperature) values(?,?,?)";
		Object [] args = {temp.getSensorExtAddr(),temp.getRecordTime(),temp.getTemperature()};
		int [] argTypes = {Types.VARCHAR,Types.VARCHAR,Types.FLOAT};
		return manager.add(sql, args, argTypes);
	}
	
	/**
	 *orderbyNumber
	 */
	public List<Temperature> getDataUseLimit(String extAddr, String startTime,
			String endTime, int limitNumber, int orderbyNumber) {
		// TODO Auto-generated method stub
		String orderbyRule = orderbyNumber == 1?"":"desc";
		String sql = "select * from temperature where sensorExtAddr = ? and (recordTime between ? and ?) order by temperature "
				+orderbyRule+ " limit ?";
		Object [] args = {extAddr,startTime,endTime,limitNumber};
		int [] argTypes = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		return manager.query(sql, args,argTypes,RowMapperEnum.TEMP.getMapperName());
	}

	public List<Temperature> getExceptionDataByTime(String extAddr,
			String startTime, String endTime, float maxTemp, float minTemp) {
		// TODO Auto-generated method stub
		String sql = "select * from temperature where sensorExtAddr = ? and (recordTime between ? and ?) "
				+ "and (temperature not between ? and ?) order by recordTime";
		Object [] args = {extAddr,startTime,endTime,minTemp,maxTemp};
		int [] argTypes = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.FLOAT,Types.FLOAT};
		return manager.query(sql, args,argTypes,RowMapperEnum.TEMP.getMapperName());

	}

	public List<Temperature> getExceptionDataUseLimit(String extAddr,
			String startTime, String endTime, int limitNumber,
			int orderbyNumber, float maxTemp, float minTemp) {
		// TODO Auto-generated method stub
		String orderbyRule = orderbyNumber == 1?"":"desc";
		String sql = "select * from temperature where sensorExtAddr = ? and (recordTime between ? and ?) "
				+ " and (temperature not between ? and ?) order by temperature "
				+orderbyRule+ " limit ?";
		Object [] args = {extAddr,startTime,endTime,maxTemp,minTemp,limitNumber};
		int [] argTypes = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.FLOAT,Types.FLOAT,Types.INTEGER};
		return manager.query(sql, args,argTypes,RowMapperEnum.TEMP.getMapperName());
	}

	public int getNormalNumber(String extAddr, String startTime,
			String endTime, float maxTemp, float minTemp) {
		// TODO Auto-generated method stub
		String sql = "select count(*) num from temperature where sensorExtAddr = ? and (recordTime between ? and ?) "
				+ " and (temperature  between ? and ?)";
		Object [] args = {extAddr,startTime,endTime,minTemp,maxTemp};
		int [] argTypes = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.FLOAT,Types.FLOAT};
		List<Map<String,Object>> numList = manager.queryForList(sql, args, argTypes);
		if(numList!=null && numList.size() == 1){
			return Integer.parseInt( numList.get(0).get("num").toString());
		}
		return 0;
	}

	public int getExceedNumber(String extAddr, String startTime,
			String endTime, float maxTemp) {
		// TODO Auto-generated method stub
		String sql = "select count(*) num from temperature where sensorExtAddr = ? and (recordTime between ? and ?) "
				+ " and (temperature  > ? )";
		Object [] args = {extAddr,startTime,endTime,maxTemp};
		int [] argTypes = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.FLOAT};
		List<Map<String,Object>> numList = manager.queryForList(sql, args, argTypes);
		if(numList!=null && numList.size() == 1){
			return Integer.parseInt( numList.get(0).get("num").toString());
		}
		return 0;
	}

	public int getLowerNumber(String extAddr, String startTime, String endTime,
			float minTemp) {
		// TODO Auto-generated method stub
		String sql = "select count(*) num from temperature where sensorExtAddr = ? and (recordTime between ? and ?) "
				+ " and (temperature  < ? )";
		Object [] args = {extAddr,startTime,endTime,minTemp};
		int [] argTypes = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.FLOAT};
		List<Map<String,Object>> numList = manager.queryForList(sql, args, argTypes);
		if(numList!=null && numList.size() == 1){
			return Integer.parseInt( numList.get(0).get("num").toString());
		}
		return 0;
	}

}
