package com.jtang.model;

import java.sql.Types;

import com.jtang.annotation.DBTable;
import com.jtang.annotation.TableColumn;

@DBTable(primaryKeyName = "processNumber", tableName = "process_record")
public class ProcessRecord {
	@TableColumn(columnName = "processNumber", type = Types.VARCHAR)
	private String processNumber;
	
	@TableColumn(columnName = "rawIdList", type = Types.VARCHAR)
	private String rawIdList;
	
	@TableColumn(columnName = "processTime", type = Types.VARCHAR)
	private String processTime;
	
	@TableColumn(columnName = "processUser", type = Types.VARCHAR)
	private String processUser;
	
	@TableColumn(columnName = "processLoc", type = Types.VARCHAR)
	private String processLoc = "";
	
	@TableColumn(columnName = "processEnv", type = Types.VARCHAR)
	private String processEnv = "";
	
	@TableColumn(columnName = "enterpriseId", type = Types.VARCHAR)
	private String enterpriseId;
	
	@TableColumn(columnName = "rawCountList", type = Types.VARCHAR)
	private String rawCountList;
	
	/**
	 * @return the rawCountList
	 */
	public String getRawCountList() {
		return rawCountList;
	}
	/**
	 * @param rawCountList the rawCountList to set
	 */
	public void setRawCountList(String rawCountList) {
		this.rawCountList = rawCountList;
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
	 * @return the processNumber
	 */
	public String getProcessNumber() {
		return processNumber;
	}
	/**
	 * @param processNumber the processNumber to set
	 */
	public void setProcessNumber(String processNumber) {
		this.processNumber = processNumber;
	}
	/**
	 * @return the rawIdList
	 */
	public String getRawIdList() {
		return rawIdList;
	}
	/**
	 * @param rawIdList the rawIdList to set
	 */
	public void setRawIdList(String rawIdList) {
		this.rawIdList = rawIdList;
	}
	/**
	 * @return the processTime
	 */
	public String getProcessTime() {
		return processTime;
	}
	/**
	 * @param processTime the processTime to set
	 */
	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}
	/**
	 * @return the processUser
	 */
	public String getProcessUser() {
		return processUser;
	}
	/**
	 * @param processUser the processUser to set
	 */
	public void setProcessUser(String processUser) {
		this.processUser = processUser;
	}
	/**
	 * @return the processLoc
	 */
	public String getProcessLoc() {
		return processLoc;
	}
	/**
	 * @param processLoc the processLoc to set
	 */
	public void setProcessLoc(String processLoc) {
		this.processLoc = processLoc;
	}
	/**
	 * @return the processEnv
	 */
	public String getProcessEnv() {
		return processEnv;
	}
	/**
	 * @param processEnv the processEnv to set
	 */
	public void setProcessEnv(String processEnv) {
		this.processEnv = processEnv;
	}
	
	
}
