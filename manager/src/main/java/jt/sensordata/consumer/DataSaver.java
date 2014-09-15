package jt.sensordata.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jtang.servicethread.CheckZigbee;
import com.jtang.servicethread.SensorHandler;
import com.jtang.servicethread.StoreTemp;
import com.jtang.servicethread.UpdateSensorWorkTime;

public class DataSaver {
	
    
    public CheckZigbee checkZigbee;//由Spring注入
    
    public StoreTemp storeTemp;  //由Spring注入，定期存储传感器数据
    
    public UpdateSensorWorkTime updateSensorWorkTime;
    
    public SensorHandler sensorHandler;
    
    

	/**
	 * @return the sensorHandler
	 */
	public SensorHandler getSensorHandler() {
		return sensorHandler;
	}

	/**
	 * @param sensorHandler the sensorHandler to set
	 */
	public void setSensorHandler(SensorHandler sensorHandler) {
		this.sensorHandler = sensorHandler;
	}

	/**
	 * @return the checkZigbee
	 */
	public CheckZigbee getCheckZigbee() {
		return checkZigbee;
	}

	/**
	 * @param checkZigbee the checkZigbee to set
	 */
	public void setCheckZigbee(CheckZigbee checkZigbee) {
		this.checkZigbee = checkZigbee;
	}

	/**
	 * @return the storeTemp
	 */
	public StoreTemp getStoreTemp() {
		return storeTemp;
	}

	/**
	 * @param storeTemp the storeTemp to set
	 */
	public void setStoreTemp(StoreTemp storeTemp) {
		this.storeTemp = storeTemp;
	}

	/**
	 * @return the updateSensorWorkTime
	 */
	public UpdateSensorWorkTime getUpdateSensorWorkTime() {
		return updateSensorWorkTime;
	}

	/**
	 * @param updateSensorWorkTime the updateSensorWorkTime to set
	 */
	public void setUpdateSensorWorkTime(UpdateSensorWorkTime updateSensorWorkTime) {
		this.updateSensorWorkTime = updateSensorWorkTime;
	}
    
    public void startAllSaver(){
    	ExecutorService exec = Executors.newCachedThreadPool();
    	exec.execute(checkZigbee);
    	exec.execute(storeTemp);
    	exec.execute(updateSensorWorkTime);
    	exec.execute(sensorHandler);
    	exec.shutdown();
    }
}
