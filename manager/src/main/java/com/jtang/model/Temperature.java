/**
 * 
 */
package com.jtang.model;

/**
 * @author Administartor
 *
 */
public class Temperature {
	public String sensorExtAddr;
	public String recordTime;
	public float temperature;
	/**
	 * @return the sensorExtAddr
	 */
	public String getSensorExtAddr() {
		return sensorExtAddr;
	}
	/**
	 * @param sensorExtAddr the sensorExtAddr to set
	 */
	public void setSensorExtAddr(String sensorExtAddr) {
		this.sensorExtAddr = sensorExtAddr;
	}
	/**
	 * @return the recordTime
	 */
	public String getRecordTime() {
		return recordTime;
	}
	/**
	 * @param recordTime the recordTime to set
	 */
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
	/**
	 * @return the temperature
	 */
	public float getTemperature() {
		return temperature;
	}
	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	

}
