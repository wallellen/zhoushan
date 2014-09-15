/**
 * 
 */
package com.jtang.servicethread;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.jtang.service.ISensorService;

import jt.sensordata.bean.GlobalVariable;

/**
 * @author zxy
 *
 */
public class UpdateSensorWorkTime implements Runnable {
	
	public Logger logger= Logger.getLogger(this.getClass());
	public int scannerInterval = 1; //this scanner will start in 1 minutes
	public ISensorService sensorService;
	

	public ISensorService getSensorService() {
		return sensorService;
	}


	public void setSensorService(ISensorService sensorService) {
		this.sensorService = sensorService;
	}	

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			
			try {
				TimeUnit.MINUTES.sleep(scannerInterval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ConcurrentHashMap<String, String> shortToExt
				= GlobalVariable.shortToExt;
			
			Iterator<?> it = shortToExt.entrySet().iterator();
			
			while(it.hasNext()){
				@SuppressWarnings("unchecked")
				Map.Entry<String,String> entry = (Entry<String,String>) it.next();
				String extAddr = entry.getValue();
				int result = sensorService.updateWorkTime(extAddr, 1);
				if(result == 0){
					logger.error("尝试更新传感器工作时间，但发生了错误！");
				}
			}
		}
					
	}

}
