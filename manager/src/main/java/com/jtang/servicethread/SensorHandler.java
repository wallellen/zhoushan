package com.jtang.servicethread;

import jt.sensordata.bean.GlobalVariable;

import com.jtang.model.Sensor;
import com.jtang.service.ISensorService;

public class SensorHandler implements Runnable{

	
	public ISensorService sensorService; //由Spring注入
	
	
	
	
	/**
	 * @return the sensorService
	 */
	public ISensorService getSensorService() {
		return sensorService;
	}

	/**
	 * @param sensorService the sensorService to set
	 */
	public void setSensorService(ISensorService sensorService) {
		this.sensorService = sensorService;
	}

	public void run() {
		// TODO Auto-generated method stub
		while(true){
			Sensor sensor = null;
		
			try {
				sensor = GlobalVariable.unHandledSensor.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			refreshToDatabase(sensor);
		}
	}
	
	/**
	 * 开辟一个新线程将数据插入或更新到数据库
	 * 如果是更新，则不更新的字段有：Position，creator，createTime,workTime
	 * 如果是插入，则mender和mendTime字段插入“unknown”
	 * @param sensor
	 */
	public void refreshToDatabase(Sensor sensor){
		if(sensorService.isSensorExists(sensor.getExtAddr())){
			sensorService.updateASensor(sensor); 
		}else{
			sensor.setMender("unKnown");
			sensor.setMendTime("unKnow");
			sensorService.addASensor(sensor);
		}
	}

}
