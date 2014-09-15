package com.jtang.model;

import java.sql.Types;

import com.jtang.annotation.DBTable;
import com.jtang.annotation.TableColumn;

@DBTable(tableName = "user",primaryKeyName = "id")
public class User {
	@TableColumn(columnName = "id",
			type = Types.VARCHAR)
	private String id;
	@TableColumn(columnName = "name",
			type = Types.VARCHAR)
	private String name;
	@TableColumn(columnName = "passwd",
			type = Types.VARCHAR)
	private String passwd;
	@TableColumn(columnName = "sex",
			type = Types.INTEGER)
	private int sex;
	@TableColumn(columnName = "sex",
			type = Types.INTEGER)
	private int positionId;
	@TableColumn(columnName = "enterpriseId",
			type = Types.VARCHAR)
	private String enterpriseId;
	@TableColumn(columnName = "storageList",
			type = Types.VARCHAR)
	private String storageList;
	@TableColumn(columnName = "defaultStorageId",
			type = Types.INTEGER)
	private int defaultStorageId;
	@TableColumn(columnName = "privilege",
			type = Types.INTEGER)
	private int privilege;
	
	public int getPrivilege() {
		return privilege;
	}
	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}
	/**
	 * @return the defaultStorageId
	 */
	public int getDefaultStorageId() {
		return defaultStorageId;
	}
	/**
	 * @param defaultStorageId the defaultStorageId to set
	 */
	public void setDefaultStorageId(int defaultStorageId) {
		this.defaultStorageId = defaultStorageId;
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
	 * @return the storageList
	 */
	public String getStorageList() {
		return storageList;
	}
	/**
	 * @param storageList the storageList to set
	 */
	public void setStorageList(String storageList) {
		this.storageList = storageList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public int getPositionId() {
		return positionId;
	}
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	
    public int hasPrivilegeStorage()
    {
    	return this.privilege & 4;
    }
    
    public int hasPrivilegeTemp()
    {
    	return this.positionId & 2;
    }

    public int hasPrivilegeTrace()
    {
    	return this.privilege & 1;
    }
}
