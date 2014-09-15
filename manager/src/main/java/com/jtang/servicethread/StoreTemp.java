/**
 * 
 */
package com.jtang.servicethread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import jt.sensordata.bean.GlobalVariable;
import jt.sensordata.main.ClientManager;

import com.jtang.dao.BasicManagerDao;
import com.jtang.dao.sqlUtil.Selector;
import com.jtang.dao.sqlUtil.Zql;
import com.jtang.model.Message;
import com.jtang.model.Temperature;
import com.jtang.service.ITempService;

/**
 * @author Administartor
 *
 */
public class StoreTemp implements Runnable {

	public ITempService tempService;
	private BasicManagerDao manager;
	public int scannerInterval = 5; //this scanner will start in 1 minutes
	private ExecutorService threadPool;
	
	
	/**
	 * @return the tempService
	 */
	public ITempService getTempService() {
		return tempService;
	}

	/**
	 * @param tempService the tempService to set
	 */
	public void setTempService(ITempService tempService) {
		this.tempService = tempService;
	}
	
	

	/**
	 * @param manager the manager to set
	 */
	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}
	
	public StoreTemp(){
		threadPool = Executors.newFixedThreadPool(3);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				TimeUnit.MINUTES.sleep(scannerInterval);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				ConcurrentHashMap<String, Float> averageTemp = GlobalVariable.averageTemp;
				
				Iterator<?> it = averageTemp.entrySet().iterator();
				
				while(it.hasNext()){
					@SuppressWarnings("unchecked")
					Map.Entry<String, Float> entry = (Map.Entry<String, Float>) it.next();
					String extAddr =  entry.getKey();
					Float temperature = entry.getValue();
					Temperature temp = new Temperature();
					temp.setSensorExtAddr(extAddr);
					temp.setTemperature(temperature);
					temp.setRecordTime(formatter.format(new Date()));					
					tempService.insertRecord(temp);	
					threadPool.submit(new TempExceptionAlarm(temp));
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private class TempExceptionAlarm implements Runnable{
		private Temperature temp;
		
		public TempExceptionAlarm(Temperature temp){
			this.temp = temp;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Selector sel = 
					Zql.select("extAddr","sensor.name sensorName","maxTemp","minTemp","storage.name storageName","storage.id storageId")
					.from("sensor").join("storage", "sensor.storageId", "=", "storage.id")
					.where(Zql.condition("extAddr", "=", temp.getSensorExtAddr()));
			List<Map<String,Object>> dataList = manager.queryForList(sel);
			System.out.println("i am here");
			System.out.println(dataList.toString());
			
			if(dataList !=null && dataList.size() == 1){
				Map<String,Object> data = dataList.get(0);
				if(temp.getTemperature() > (Float)data.get("maxTemp") || temp.getTemperature() < (Float)data.get("minTemp")){
					Message msg = new Message();
					msg.setFromWho("SensorNet");
					String now = GlobalVariable.formatter.format(new Date());
					msg.setTime(now);
					msg.setContent("");
					msg.setTitle("来自传感器的温度预警！");
					String content = "["+data.get("storageName")+"]的"
							+"["+data.get("sensorName")+"]采集到的温度数据";
					if(temp.getTemperature() > (Float)data.get("maxTemp")){
						content += "高于最高阈值" + (temp.getTemperature() - (Float)data.get("maxTemp") + "").substring(0,4) + "度";
					}else{
						content += "低于最低阈值" + ((Float)data.get("maxTemp") - temp.getTemperature() + "").substring(0,4) + "度";
					}
					msg.setContent(content);
					String storageId = data.get("storageId").toString();
					sel = Zql.select("Id").from("user")
							.where(Zql.condition("storageList", "like", "%:"+storageId+":%")
									.or(Zql.condition("storageList", "like", "%:" + storageId))
									.or(Zql.condition("storageList", "like", storageId + ":%"))
									.or(Zql.condition("storageList", "like", storageId))
									);
					List<Map<String,Object>> userList = manager.queryForList(sel);
					
					for(Map<String,Object> user : userList){
						msg.setToWho(user.get("Id").toString());
						msg.setMessageId("SensorNet" + new Date().getTime() + user.get("Id"));
						ClientManager.sendMessageToPerson(user.get("Id").toString(), msg, true);
					}
					
				}
			}
		}
		
	}

}
