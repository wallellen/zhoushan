package com.jtang.model;

import java.util.Date;

public class ProductQuery {
	//产品数据
	private int Id;
	private String ProName;
	private String ProBarcode;
	private Date ProDate;
	//存储数据
	private String StoName;
	private Date StoStime;
	private Date StoEtime;
	private float StoTemp;
	//运输数据
	private String TranSplace;
	private String TranEplace;
	private Date TranStime;
	private Date TranEtime;
	private float TranTemp;
	//原材料数据
	private String RmName;
	private String RmManu;
	private String RmPlace;
	private Date RmDate;
	//记录员姓名
	private String RecorderName;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getProName() {
		return ProName;
	}
	public void setProName(String proName) {
		ProName = proName;
	}
	public String getProBarcode() {
		return ProBarcode;
	}
	public void setProBarcode(String proBarcode) {
		ProBarcode = proBarcode;
	}
	public Date getProDate() {
		return ProDate;
	}
	public void setProDate(Date proDate) {
		ProDate = proDate;
	}
	public String getStoName() {
		return StoName;
	}
	public void setStoName(String stoName) {
		StoName = stoName;
	}
	public Date getStoStime() {
		return StoStime;
	}
	public void setStoStime(Date stoStime) {
		StoStime = stoStime;
	}
	public Date getStoEtime() {
		return StoEtime;
	}
	public void setStoEtime(Date stoEtime) {
		StoEtime = stoEtime;
	}
	public float getStoTemp() {
		return StoTemp;
	}
	public void setStoTemp(float stoTemp) {
		StoTemp = stoTemp;
	}
	public String getTranSplace() {
		return TranSplace;
	}
	public void setTranSplace(String tranSplace) {
		TranSplace = tranSplace;
	}
	public String getTranEplace() {
		return TranEplace;
	}
	public void setTranEplace(String tranEplace) {
		TranEplace = tranEplace;
	}
	public Date getTranStime() {
		return TranStime;
	}
	public void setTranStime(Date tranStime) {
		TranStime = tranStime;
	}
	public Date getTranEtime() {
		return TranEtime;
	}
	public void setTranEtime(Date tranEtime) {
		TranEtime = tranEtime;
	}
	public float getTranTemp() {
		return TranTemp;
	}
	public void setTranTemp(float tranTemp) {
		TranTemp = tranTemp;
	}
	public String getRmName() {
		return RmName;
	}
	public void setRmName(String rmName) {
		RmName = rmName;
	}
	public String getRmManu() {
		return RmManu;
	}
	public void setRmManu(String rmManu) {
		RmManu = rmManu;
	}
	public String getRmPlace() {
		return RmPlace;
	}
	public void setRmPlace(String rmPlace) {
		RmPlace = rmPlace;
	}
	public Date getRmDate() {
		return RmDate;
	}
	public void setRmDate(Date rmDate) {
		RmDate = rmDate;
	}
	public String getRecorderName() {
		return RecorderName;
	}
	public void setRecorderName(String recorderName) {
		RecorderName = recorderName;
	}

}