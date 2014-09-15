package com.jtang.model;

import java.sql.Types;

import com.jtang.annotation.DBTable;
import com.jtang.annotation.TableColumn;

@DBTable(tableName = "enterprise",primaryKeyName = "id")
public class Enterprise {
	
	@TableColumn(columnName = "id",
			type = Types.VARCHAR)
	private String id;
	@TableColumn(columnName = "description",
			type = Types.VARCHAR)
	private String description;
	@TableColumn(columnName = "name",
			type = Types.VARCHAR)
	private String name;
	@TableColumn(columnName = "address",
			type = Types.VARCHAR)
	private String address;
	@TableColumn(columnName = "email",
			type = Types.VARCHAR)
	private String email;
	@TableColumn(columnName = "traceService",
			type = Types.INTEGER)
	private int traceService;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTraceService() {
		return traceService;
	}

	public void setTraceService(int traceService) {
		this.traceService = traceService;
	}

	public int getStorageService() {
		return storageService;
	}

	public void setStorageService(int storageService) {
		this.storageService = storageService;
	}

	public int getTempService() {
		return tempService;
	}

	public void setTempService(int tempService) {
		this.tempService = tempService;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@TableColumn(columnName = "storageService",
			type = Types.INTEGER)
	private int storageService;
	@TableColumn(columnName = "tempService",
			type = Types.INTEGER)
	private int tempService;
	@TableColumn(columnName = "phone",
			type = Types.VARCHAR)
	private String phone;
	@TableColumn(columnName = "registerTime",
			type = Types.VARCHAR)
	private String registerTime;
	@TableColumn(columnName = "withdrawTime",
			type = Types.VARCHAR)
	private String withdrawTime;
	@TableColumn(columnName = "isValid",
			type = Types.INTEGER)
	private int isValid;
	@TableColumn(columnName = "isDue",
			type = Types.INTEGER)
	private int isDue;

	public String getRegisterTime() {
		return registerTime;
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public int getIsDue() {
		return isDue;
	}

	public void setIsDue(int isDue) {
		this.isDue = isDue;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(String withdrawTime) {
		this.withdrawTime = withdrawTime;
	}

}
