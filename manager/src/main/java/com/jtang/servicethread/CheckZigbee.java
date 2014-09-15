package com.jtang.servicethread;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.jtang.service.ISensorService;

import jt.sensordata.bean.GlobalVariable;
import jt.sensordata.main.ClientManager;

public class CheckZigbee implements Runnable {

	public int scannerInterval = 1; //this scanner will start in 1 minutes
	public Logger logger= Logger.getLogger(this.getClass());
	public ISensorService sensorService;
	

	public ISensorService getSensorService() {
		return sensorService;
	}


	public void setSensorService(ISensorService sensorService) {
		this.sensorService = sensorService;
	}	

	public void run() {
		// TODO Auto-generated method stub
		while(true){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, String>>> 
				netWorkInfo = GlobalVariable.networkInfo;
			Iterator<?> keyIsStorage = netWorkInfo.entrySet().iterator();
			while(keyIsStorage.hasNext()){
				@SuppressWarnings("unchecked")
				Map.Entry<String, ConcurrentHashMap<String, ConcurrentHashMap<String, String>>>  
					firstEntry = (Entry<String, ConcurrentHashMap<String, ConcurrentHashMap<String, String>>>)
									keyIsStorage.next();
				String storageId = firstEntry.getKey();
				ConcurrentHashMap<String, ConcurrentHashMap<String, String>> 
					realNetwork = firstEntry.getValue();
				Iterator<?> keyIsAddr = realNetwork.entrySet().iterator();
				while(keyIsAddr.hasNext()){
					@SuppressWarnings("unchecked")
					Map.Entry<String, ConcurrentHashMap<String, String>> secondEntry = 
							(Entry<String, ConcurrentHashMap<String, String>>) keyIsAddr.next();
					String shortAddr = secondEntry.getKey();
					ConcurrentHashMap<String, String> sensorValue
						= secondEntry.getValue();
					String newTime = sensorValue.get("newTime");
					long diff = 0;
					try {
						diff = new Date().getTime() - formatter.parse(newTime).getTime();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(diff>5*1000*60){
						keyIsAddr.remove(); //update the networkinfo
						String pExtAddr = GlobalVariable.shortToExt.get(shortAddr+","+storageId);
						if(pExtAddr != null) GlobalVariable.averageTemp.remove(pExtAddr);
						GlobalVariable.shortToExt.remove(shortAddr+","+storageId);
						
						//inform client to update graph
						ClientManager.sendTopToAllClient(storageId);
					}
				}
			}
				
			try {
				TimeUnit.MINUTES.sleep(scannerInterval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	

}
