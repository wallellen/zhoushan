package com.jtang.model;

import com.jtang.annotation.DBTable;
import com.jtang.annotation.TableColumn;

@DBTable(primaryKeyName = "saleNumber", tableName = "sale_record")
public class SaleRecord {
	
	@TableColumn(columnName = "saleNumber")
	private String saleNumber;
	
	@TableColumn(columnName = "saleTime")
	private String saleTime;
	
	@TableColumn(columnName = "salePlace")
	private String salePlace;
	
	@TableColumn(columnName = "enterpriseId")
	private String enterpriseId;
	
	@TableColumn(columnName = "freezerNumber")
	private String freezerNumber;
	
	@TableColumn(columnName = "saleMarket")
	private String saleMarket;
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
	 * @return the saleTime
	 */
	public String getSaleTime() {
		return saleTime;
	}
	/**
	 * @param saleTime the saleTime to set
	 */
	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}
	/**
	 * @return the salePlace
	 */
	public String getSalePlace() {
		return salePlace;
	}
	/**
	 * @param salePlace the salePlace to set
	 */
	public void setSalePlace(String salePlace) {
		this.salePlace = salePlace;
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
	 * @return the freezerNumber
	 */
	public String getFreezerNumber() {
		return freezerNumber;
	}
	/**
	 * @param freezerNumber the freezerNumber to set
	 */
	public void setFreezerNumber(String freezerNumber) {
		this.freezerNumber = freezerNumber;
	}
	/**
	 * @return the saleMarket
	 */
	public String getSaleMarket() {
		return saleMarket;
	}
	/**
	 * @param saleMarket the saleMarket to set
	 */
	public void setSaleMarket(String saleMarket) {
		this.saleMarket = saleMarket;
	}
	
	
}
