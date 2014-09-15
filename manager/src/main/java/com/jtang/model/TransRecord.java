package com.jtang.model;

import java.sql.Types;

import com.jtang.annotation.DBTable;
import com.jtang.annotation.TableColumn;

@DBTable(primaryKeyName = "transportNumber", tableName = "transportation_record")
public class TransRecord {
	@TableColumn(columnName = "transportNumber")
	private String transportNumber;
	
	@TableColumn(columnName = "startPlace")
	private String startPlace;
	
	@TableColumn(columnName = "endPlace")
	private String endPlace;
	
	@TableColumn(columnName = "startTime")
	private String startTime;
	
	@TableColumn(columnName = "endTime")
	private String endTime;
	
	@TableColumn(columnName = "enterpriseId")
	private String enterpriseId;
	
	//执行此次运输任务的车辆或船只 或其它工具的牌照
	@TableColumn(columnName = "license")
	private String license;
	
	@TableColumn(columnName = "driver")
	private String driver;
	
	/**
	 * @return the driver
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * @param driver the driver to set
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}

	@TableColumn(columnName = "status", type = Types.INTEGER)
	private int status;

	/**
	 * @return the transportNumber
	 */
	public String getTransportNumber() {
		return transportNumber;
	}

	/**
	 * @param transportNumber the transportNumber to set
	 */
	public void setTransportNumber(String transportNumber) {
		this.transportNumber = transportNumber;
	}

	/**
	 * @return the startPlace
	 */
	public String getStartPlace() {
		return startPlace;
	}

	/**
	 * @param startPlace the startPlace to set
	 */
	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	/**
	 * @return the endPlace
	 */
	public String getEndPlace() {
		return endPlace;
	}

	/**
	 * @param endPlace the endPlace to set
	 */
	public void setEndPlace(String endPlace) {
		this.endPlace = endPlace;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the enterpriseId
	 */
	public String getEnterpriseId() {
		return enterpriseId;
	}

	/**
	 * @param enterpriseId the enterpriseId to set
	 */
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the license
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * @param license the license to set
	 */
	public void setLicense(String license) {
		this.license = license;
	}
	
	
}
