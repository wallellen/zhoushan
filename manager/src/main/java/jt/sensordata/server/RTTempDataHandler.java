package jt.sensordata.server;



import jt.sensordata.bean.GlobalVariable;
import jt.sensordata.main.ClientManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yongboy.socketio.server.IOHandlerAbs;
import com.yongboy.socketio.server.transport.IOClient;

/**
 * 
 * @author Administartor
 *使用此Handler，客户端的调用方式类似于：
 *socket.emit('SensorData', '45FF,storageid');
 *socket.emit('ChangeSensor', '45FF,storageid');
 */
public class RTTempDataHandler extends IOHandlerAbs {

	private Logger log = Logger.getLogger(this.getClass());
	
	public void OnConnect(IOClient client) {
		// TODO Auto-generated method stub
		log.debug("A user connected :: "+client.getSessionID());
		
	}

	
	public void OnDisconnect(IOClient client) {
		// TODO Auto-generated method stub
		log.debug("A user disconnected :: " + client.getSessionID());
		//将该客户端从clientList当中剔除，节约资源
		ClientManager.removeClient(client,2);

		client.disconnect();
	}

	
	public void OnMessage(IOClient client, String oriMessage) {
		// TODO Auto-generated method stub
		String jsonString = oriMessage.substring(oriMessage.indexOf('{'));
		jsonString = jsonString.replaceAll("\\\\", "");
		JSONObject jsonObject = JSON.parseObject(jsonString);
		String eventName = jsonObject.get("name").toString();
		if (eventName.equals("SensorData")) {
			 JSONArray argsArray = jsonObject.getJSONArray("args");
             String [] requestContent = argsArray.getString(0).split(",");
             String sensorAddr = requestContent[0];
             String storageId = requestContent[1];
			 log.debug("User "+client.getSessionID()+" request the temperature data of "+sensorAddr);
			 //将请求某传感器数据的client加入列表当中用于统一发送，以设备短地址为标识符
			 ClientManager.addClientToList(client, getExtendAddr(sensorAddr,storageId), 2);
			 
		}else if(eventName.equals("ChangeSensor")){
			
			JSONArray argsArray = jsonObject.getJSONArray("args");
			String [] requestContent = argsArray.getString(0).split(",");
			String sensorAddr = requestContent[0];
			String storageId = requestContent[1];
			log.debug("User "+client.getSessionID()+" change the sensor to "+sensorAddr + " in storage " + storageId);
			ClientManager.removeClient(client,2);
			ClientManager.addClientToList(client, getExtendAddr(sensorAddr,storageId), 2);
		}
		
		
	}


	public void OnShutdown() {
		// TODO Auto-generated method stub
		log.debug("A connect shutdown....");
	}
	
	public String getExtendAddr(String shortAddr,String storageId){
		String extAddr =  GlobalVariable.shortToExt.get(shortAddr+","+storageId);
		if(extAddr == null){
			 log.error("we can't find the extend address of " + shortAddr +"in storage " + storageId);
			 return "";
		}
		return extAddr;
	}

}
