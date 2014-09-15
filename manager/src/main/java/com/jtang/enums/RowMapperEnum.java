package com.jtang.enums;

/**
 * 表Mapper的枚举
 * @author yyj
 *
 */
public enum RowMapperEnum {
	
	/**
	 * 平台管理
	 */
	ENTERPRISE("EnterpriseMapper"),
	STORAGESERVICE("StorageServiceMapper"),
	TEMPSERVICE("TempServiceMapper"),
	TRACESERVICE("TraceServiceMapper"),
	
	/**
	 * 权限控制
	 */
	PRIVILEGEUSER( "PrivilegeUserMapper" ),
	PRIVILEGE("PrivilegeMapper"),
	
	/**
	 * Message model
	 */
	MESSAGE("MessageMapper"),
	/*
	 * 出入库
	 */
	 INOUT( "InOutMapper"  ),
	 INOUTRAW("InoutRawMapper"),
	 INOUTWITHPRONAME("InOutWithProNameMapper"),
	 INVENTORY("InventoryMapper"),
	 /*
	  *温控 
	  */
	 STORAGE("StorageMapper"),
	 USER("UserMapper"),
	 SENSOR("SensorMapper"),
	 TEMP("TempMapper"),
	 /*
	  * 溯源
	  */
	 PRODUCTQUERY("ProductQueryMapper"),
	 PRODUCTTRACE("ProductTraceMapper"),
	 RAWMATERIAL("RawmaterialMapper"),
	 TRANSRECORD("TransRecordMapper"),
	 STORERECORD("StoreRecordMapper"),
	 PROCESSRECORD("ProcessRecordMapper"),
	 VIRTUALPRODUCTION("VirtualProductionMapper"),
	 SALERECORD("SaleRecordMapper");
	 
	 private final String className;
	 
	 private final String packageName = "com.jtang.dao.mapper.";
	 
	 RowMapperEnum( String className  ){
		 this.className = className ;
	 }
	
	 public String getMapperName () {
		 return this.packageName+this.className;
	 }
	 
	 public String toString(){
		 return this.packageName+this.className;
	 }
	 
}
