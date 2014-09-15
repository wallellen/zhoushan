package jt.sensordata.bean;

import java.io.Serializable;

public class OriginalTemp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String temperature;
	public String deviceClass;
	public String shortAddr;
	public String extAddr ;
	public String parentShortAddr;
	public String enterpriseId;
	public String storageId;

	/**
	 * @return the temperature
	 */
	public String getTemperature() {
		return temperature;
	}
	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	/**
	 * @return the deviceClass
	 */
	public String getDeviceClass() {
		return deviceClass;
	}
	/**
	 * @param deviceClass the deviceClass to set
	 */
	public void setDeviceClass(String deviceClass) {
		this.deviceClass = deviceClass;
	}
	/**
	 * @return the shortAddr
	 */
	public String getShortAddr() {
		return shortAddr;
	}
	/**
	 * @param shortAddr the shortAddr to set
	 */
	public void setShortAddr(String shortAddr) {
		this.shortAddr = shortAddr;
	}
	/**
	 * @return the extAddr
	 */
	public String getExtAddr() {
		return extAddr;
	}
	/**
	 * @param extAddr the extAddr to set
	 */
	public void setExtAddr(String extAddr) {
		this.extAddr = extAddr;
	}
	/**
	 * @return the parentShortAddr
	 */
	public String getParentShortAddr() {
		return parentShortAddr;
	}
	/**
	 * @param parentShortAddr the parentShortAddr to set
	 */
	public void setParentShortAddr(String parentShortAddr) {
		this.parentShortAddr = parentShortAddr;
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
	 * @return the storageId
	 */
	public String getStorageId() {
		return storageId;
	}
	/**
	 * @param storageId the storageId to set
	 */
	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return temperature+","+deviceClass+","+shortAddr+","
				+extAddr+","+parentShortAddr+","+storageId+","+enterpriseId
				;
	}
	
	
	
}
