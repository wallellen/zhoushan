package jt.sensordata.bean;

import java.io.Serializable;

public class SensorCommand implements Serializable{

	// String cmd = "0x"+sensorAddr+","+cmdId+","+arg0+","; 
	/**
	 * 
	 */
	private static final long serialVersionUID = -8632537516495182598L;

	public String sensorHead = "";
	public String sensorAddr;
	public String cmdId;
	public String arg0;
	/**
	 * @return the sensorHead
	 */
	public String getSensorHead() {
		return sensorHead;
	}
	/**
	 * @param sensorHead the sensorHead to set
	 */
	public void setSensorHead(String sensorHead) {
		this.sensorHead = sensorHead;
	}
	/**
	 * @return the sensorAddr
	 */
	public String getSensorAddr() {
		return sensorAddr;
	}
	/**
	 * @param sensorAddr the sensorAddr to set
	 */
	public void setSensorAddr(String sensorAddr) {
		this.sensorAddr = sensorAddr;
	}
	/**
	 * @return the cmdId
	 */
	public String getCmdId() {
		return cmdId;
	}
	/**
	 * @param cmdId the cmdId to set
	 */
	public void setCmdId(String cmdId) {
		this.cmdId = cmdId;
	}
	/**
	 * @return the arg0
	 */
	public String getArg0() {
		return arg0;
	}
	/**
	 * @param arg0 the arg0 to set
	 */
	public void setArg0(String arg0) {
		this.arg0 = arg0;
	}
	
	
}
