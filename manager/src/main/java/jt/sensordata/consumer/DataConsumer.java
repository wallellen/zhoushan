package jt.sensordata.consumer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import com.jtang.model.Sensor;

import jt.sensordata.bean.GlobalVariable;
import jt.sensordata.bean.OriginalTemp;
import jt.sensordata.main.ClientManager;

public class DataConsumer implements Runnable {
	java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00"); 
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	ConcurrentHashMap<String, Float> averageTemp = GlobalVariable.averageTemp;
	ConcurrentHashMap<String,String> extToShort = GlobalVariable.extToShort;
	ConcurrentHashMap<String,String> shortToExt = GlobalVariable.shortToExt;
	ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, String>>> 
		networkInfo = GlobalVariable.networkInfo;
	/*
	 * just temp variable
	 */
	String extAddr = null;
	String temperature = null;
	String shortAddr = null;
	String deviceClass = null;
	String parentShortAddr = null;
	String storageId = null;
	int flag = 0; //标识是否需要更新sensor表
	/*
	 * just temp variable
	 */
	private void initalVariable(OriginalTemp oriTemp) {
		// TODO Auto-generated method stub
		extAddr = oriTemp.getExtAddr();
		temperature = oriTemp.getTemperature();
		shortAddr = oriTemp.getShortAddr();
		deviceClass = oriTemp.getDeviceClass();
		parentShortAddr = oriTemp.getParentShortAddr();
		storageId  = oriTemp.getStorageId();
	}
	
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			OriginalTemp oriTemp = null;
			try {
				oriTemp = GlobalVariable.dataQueue.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(oriTemp);
			initalVariable(oriTemp);  // should invoke this method firstly
			
			sendDataToClient();
			storeAverageTemperature();
			updateAddrRelationship();
			updateNetworkInfo();
			
			updateSensorTable(); //waiting for implement
			
		}
	}
	private void sendDataToClient() {
		// TODO Auto-generated method stub
		String nowTime = formatter.format(new Date());
		ClientManager.sendTempToAllClient(extAddr, nowTime, temperature);
	}

	private void updateSensorTable() {
		// TODO Auto-generated method stub
		if(1 == flag){
			//需要插入到数据库或者更新到数据库
			Sensor s = new Sensor();
        	s.setExtAddr(extAddr);
        	s.setShortAddr(shortAddr);
        	s.setNodeTypes(Integer.parseInt(deviceClass));
        	s.setWorkStatus(1);
        	s.setFatherNode(parentShortAddr);
        	s.setPosition("UnKnow");
        	s.setCreator("Sensor NetWork");
        	s.setCreateTime(formatter.format(new Date()));
        	s.setMender("Sensor NetWork");
        	s.setMendTime(formatter.format(new Date()));
        	s.setWorkTime(0);
        	s.setStorageId(Integer.parseInt(storageId));
        	GlobalVariable.unHandledSensor.offer(s);
		}
		flag = 0;
	}

	private void updateNetworkInfo() {
		// TODO Auto-generated method stub
		if(deviceClass!=null && shortAddr!=null && parentShortAddr!=null){
			if(networkInfo.get(storageId) == null){
				networkInfo.put(storageId, new ConcurrentHashMap<String,ConcurrentHashMap<String,String>>());
			}
			ConcurrentHashMap<String, String> cyto = networkInfo.get(storageId).get(shortAddr);
			String nowTime = formatter.format(new Date());
			if(cyto!=null){
				if(cyto.get("pointType").toString().equals(deviceClass)
						&&cyto.get("parentIp").toString().equals(parentShortAddr)){
					cyto.put("newTime", nowTime);
					//拓扑结构未发生变化,不需通知客户端
				}else{
					//拓扑结构发生了改变,需要更新
					cyto.put("pointType", deviceClass);
					cyto.put("parentIp", parentShortAddr);
					cyto.put("newTime", nowTime);
					ClientManager.sendTopToAllClient(storageId);
					//need update the sensor table
					flag = 1;
				}
			}else{
				//新增加了一个传感器或者传感器的短地址发生了变法
				cyto = new ConcurrentHashMap<String,String>();
				cyto.put("pointType", deviceClass);
				cyto.put("parentIp", parentShortAddr);
				cyto.put("newTime", nowTime);
				networkInfo.get(storageId).put(shortAddr, cyto);
				ClientManager.sendTopToAllClient(storageId);
				flag = 1;
			}
			
		}
		
	}

	private void updateAddrRelationship() {
		// TODO Auto-generated method stub	
		if(extToShort.get(extAddr) == null){
			//第一次接收到该传感器
			flag = 1;
		}else if(!extToShort.get(extAddr).equals(shortAddr)){
			//传感器存在，但是地址发送了变化
			flag = 1;			
		}
		extToShort.put(extAddr, shortAddr);
		shortToExt.put(shortAddr+","+storageId, extAddr);
		
	}
	private void storeAverageTemperature() {
		// TODO Auto-generated method stub
		
		if(averageTemp.get(extAddr) == null || averageTemp.get(extAddr) == 0){
			averageTemp.put(extAddr, Float.parseFloat(temperature));
		}else{
			averageTemp.put(extAddr, Float.parseFloat(df.format((averageTemp.get(extAddr) + Float.parseFloat(temperature))/2)));
		}
	}

}
