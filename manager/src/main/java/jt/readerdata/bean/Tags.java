package jt.readerdata.bean;

import java.io.Serializable;

public class Tags implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7713425718006894039L;
	/**
	 * 数组 标签卡号
	 */
	private String[] cardsString;
	/**
	 * 数组  标签时间
	 */
	private String[] cardsTime;
	public int getStorageId() {
		return storageId;
	}
	public void setStorageId(int storageId) {
		this.storageId = storageId;
	}
	private int cardsNum;
	private int dataLen ;
	private int storageId ;
	
	public String[] getCardsString() {
		return cardsString;
	}
	public void setCardsString(String[] cardsString) {
		this.cardsString = cardsString;
	}
	public String[] getCardsTime() {
		return cardsTime;
	}
	public void setCardsTime(String[] cardsTime) {
		this.cardsTime = cardsTime;
	}
	public int getCardsNum() {
		return cardsNum;
	}
	public void setCardsNum(int cardsNum) {
		this.cardsNum = cardsNum;
	}
	public int getDataLen() {
		return dataLen;
	}
	public void setDataLen(int dataLen) {
		this.dataLen = dataLen;
	}
}
