package com.jtang.model;

import java.sql.Types;

import com.jtang.annotation.DBTable;
import com.jtang.annotation.TableColumn;

@DBTable(primaryKeyName = "rawId", tableName = "rawmaterial")
public class Rawmaterial {
	@TableColumn(columnName = "rawId", type = Types.VARCHAR)
	private String rawId;
	
	@TableColumn(columnName = "Name", type = Types.VARCHAR)
	private String name;
	
	@TableColumn(columnName = "Manufacturer", type = Types.VARCHAR)
	private String manufacturer;
	
	@TableColumn(columnName = "ProductionPlace", type = Types.VARCHAR)
	private String productionPlace;
	
	@TableColumn(columnName = "ProductionDate", type = Types.VARCHAR)
	private String productionDate;
	
	@TableColumn(columnName = "QualityEvaluation", type = Types.VARCHAR)
	private String quality;
	/*
	 * 原料总量
	 */
	@TableColumn(columnName = "amount", type = Types.INTEGER)
	private int amount;
	/*
	 * 原料剩余量
	 */
	@TableColumn(columnName = "spare", type = Types.INTEGER)
	private int spare;
	
	/**
	 * 该批原料属于哪个企业
	 */
	@TableColumn(columnName = "enterpriseId", type = Types.VARCHAR)
	private String enterpriseId;
	
	
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
	 * @return the rawId
	 */
	public String getRawId() {
		return rawId;
	}
	/**
	 * @param rawId the rawId to set
	 */
	public void setRawId(String rawId) {
		this.rawId = rawId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}
	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	/**
	 * @return the productionPlace
	 */
	public String getProductionPlace() {
		return productionPlace;
	}
	/**
	 * @param productionPlace the productionPlace to set
	 */
	public void setProductionPlace(String productionPlace) {
		this.productionPlace = productionPlace;
	}
	/**
	 * @return the productionDate
	 */
	public String getProductionDate() {
		return productionDate;
	}
	/**
	 * @param productionDate the productionDate to set
	 */
	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	/**
	 * @return the spare
	 */
	public int getSpare() {
		return spare;
	}
	/**
	 * @param spare the spare to set
	 */
	public void setSpare(int spare) {
		this.spare = spare;
	}
	/**
	 * @return the quality
	 */
	public String getQuality() {
		return quality;
	}
	/**
	 * @param quality the quality to set
	 */
	public void setQuality(String quality) {
		this.quality = quality;
	}
	
	
	
	
}
