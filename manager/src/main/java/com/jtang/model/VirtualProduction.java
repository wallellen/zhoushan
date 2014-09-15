package com.jtang.model;


import java.sql.Types;

import com.jtang.annotation.DBTable;
import com.jtang.annotation.TableColumn;

@DBTable(tableName = "virtual_production",primaryKeyName = "qrCode")
public class VirtualProduction {
	@TableColumn(columnName = "qrCode",
			type = Types.VARCHAR)
	private String qrCode;
	
	@TableColumn(columnName = "processNumber",
			type = Types.VARCHAR)
	private String processNumber;
	
	@TableColumn(columnName = "productionName",
			type = Types.VARCHAR)
	private String productionName;
	
	@TableColumn(columnName = "rfid",
			type = Types.VARCHAR)
	private String rfid;
	
	@TableColumn(columnName = "transNumber",
			type = Types.VARCHAR)
	private String transNumber;
	
	@TableColumn(columnName = "saleNumber",
			type = Types.VARCHAR)
	private String saleNumber;
	
	@TableColumn(columnName = "enterpriseId",
			type = Types.VARCHAR)
	private String enterpriseId;
	
	@TableColumn(columnName = "productionCount",
			type = Types.INTEGER)
	private int productionCount;
	
	@TableColumn(columnName = "status",
			type = Types.INTEGER)
	private int status;

	/**
	 * @return the qrCode
	 */
	public String getQrCode() {
		return qrCode;
	}

	/**
	 * @param qrCode the qrCode to set
	 */
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
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
	 * @return the productionName
	 */
	public String getProductionName() {
		return productionName;
	}

	/**
	 * @param productionName the productionName to set
	 */
	public void setProductionName(String productionName) {
		this.productionName = productionName;
	}

	/**
	 * @return the rfid
	 */
	public String getRfid() {
		return rfid;
	}

	/**
	 * @param rfid the rfid to set
	 */
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	/**
	 * @return the transNumber
	 */
	public String getTransNumber() {
		return transNumber;
	}

	/**
	 * @param transNumber the transNumber to set
	 */
	public void setTransNumber(String transNumber) {
		this.transNumber = transNumber;
	}

	/**
	 * @return the saleNumber
	 */
	public String getSaleNumber() {
		return saleNumber;
	}

	/**
	 * @param saleNumber the saleNumber to set
	 */
	public void setSaleNumber(String saleNumber) {
		this.saleNumber = saleNumber;
	}

	/**
	 * @return the productionCount
	 */
	public int getProductionCount() {
		return productionCount;
	}

	/**
	 * @param productionCount the productionCount to set
	 */
	public void setProductionCount(int productionCount) {
		this.productionCount = productionCount;
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
	
	
	
}
