/**
 * 
 */
package com.jtang.service;

import java.util.List;

import com.jtang.model.Temperature;

/**
 * @author Administartor
 *
 */
public interface ITempService {
	
	/**
	 * 返回指定区间时间,指定传感器的温度记录
	 * @param extAddr
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Temperature> getDataByTime(String extAddr,String startTime,String endTime);
	
	/**
	 * 返回指定时间段中超出最大温度和最低温度临界值的异常数据
	 * @param extAddr
	 * @param startTime
	 * @param endTime
	 * @param maxTemp
	 * @param minTemp
	 * @return
	 */
	public List<Temperature> getExceptionDataByTime(String extAddr,String startTime,String endTime,float maxTemp,float minTemp);
	
	/**
	 * 插入一条温度记录到数据库中
	 * @param temp
	 * @return
	 */
	public int insertRecord(Temperature temp);
	
	/**
	 * 根据指定的传感器地址和起始时间与截止时间,返回该传感器在这段时间里最大的几个值或者最小的几个值
	 * @param extAddr
	 * @param startTime
	 * @param endTime
	 * @param limitNumber
	 * @param orderbyNumber
	 * @return
	 */
	public List<Temperature> getDataUseLimit(String extAddr,String startTime,String endTime,int limitNumber,int orderbyNumber);
	
	/**
	 * 根据指定传感器地址和时间，返回该传感器在这段时间中异常数据的最大的几个值或者最小的几个值
	 * @param extAddr
	 * @param startTime
	 * @param endTime
	 * @param limitNumber
	 * @param orderbyNumber
	 * @param maxTemp
	 * @param minTemp
	 * @return
	 */
	public List<Temperature> getExceptionDataUseLimit(String extAddr,String startTime,String endTime,int limitNumber,int orderbyNumber,float maxTemp,float minTemp);
	
	/**
	 * 返回正常的温度点个数
	 * @param extAddr
	 * @param startTime
	 * @param endTime
	 * @param maxTemp
	 * @param minTemp
	 * @return
	 */
	public int getNormalNumber(String extAddr,String startTime,String endTime,float maxTemp,float minTemp);
	
	/**
	 * 返回超出最大温度值的所有温度点个数
	 * @param extAddr
	 * @param startTime
	 * @param endTime
	 * @param maxTemp
	 * @return
	 */
	public int getExceedNumber(String extAddr,String startTime,String endTime,float maxTemp);
	
	/**
	 * 返回低于最小温度值的所有温度点个数
	 * @param extAddr
	 * @param startTime
	 * @param endTime
	 * @param maxTemp
	 * @return
	 */
	public int getLowerNumber(String extAddr,String startTime,String endTime,float minTemp);
}
