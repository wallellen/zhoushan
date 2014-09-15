/**
 * 
 */
package jt.sensordata.server;

import jt.sensordata.main.ClientManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yongboy.socketio.server.IOHandlerAbs;
import com.yongboy.socketio.server.transport.IOClient;

/**
 * @author Administartor
 *
 */
public class TopDataHandler extends IOHandlerAbs {
	
	Logger log = Logger.getLogger(this.getClass());

	/* (non-Javadoc)
	 * @see com.yongboy.socketio.server.IOHandler#OnConnect(com.yongboy.socketio.server.transport.IOClient)
	 */
	
	public void OnConnect(IOClient client) {
		// TODO Auto-generated method stub
		log.debug("A user connected :: "+client.getSessionID());
	}

	/* (non-Javadoc)
	 * @see com.yongboy.socketio.server.IOHandler#OnDisconnect(com.yongboy.socketio.server.transport.IOClient)
	 */
	
	public void OnDisconnect(IOClient client) {
		// TODO Auto-generated method stub
		log.debug("A user disconnected :: " + client.getSessionID());
		//remove this client from topclientlist
		ClientManager.removeClient(client, 1);
	}

	/* (non-Javadoc)
	 * @see com.yongboy.socketio.server.IOHandler#OnMessage(com.yongboy.socketio.server.transport.IOClient, java.lang.String)
	 */
	
	public void OnMessage(IOClient client, String oriMessage) {
		// TODO Auto-generated method stub
		String jsonString = oriMessage.substring(oriMessage.indexOf('{'));
		jsonString = jsonString.replaceAll("\\\\", "");
		JSONObject jsonObject = JSON.parseObject(jsonString);
		String eventName = jsonObject.get("name").toString();
		log.debug("get a message:"+oriMessage+" from "+client.getSessionID());
		if (eventName.equals("GetTop")) {
			 JSONArray argsArray = jsonObject.getJSONArray("args");
             String [] commandName = argsArray.getString(0).split(",");
             //commandName = ["Now",storageId]
             if(commandName[0].equals("Now")){
            	//立即发送一次拓扑结构
            	 ClientManager.sendTopToAClient(client,commandName[1]);
             }
            //将请求某Zigbee拓扑结构的client加入列表当中用于统一发送，以设备短地址为标识符
             ClientManager.addClientToList(client, commandName[1], 1);
		}
	}

	/* (non-Javadoc)
	 * @see com.yongboy.socketio.server.IOHandler#OnShutdown()
	 */
	
	public void OnShutdown() {
		// TODO Auto-generated method stub
		log.debug("A connect shutdown....");
	}

}
