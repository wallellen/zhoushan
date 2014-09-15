package jt.sensordata.server;

import jt.sensordata.bean.SensorCommand;
import jt.sensordata.main.ClientManager;
import org.apache.log4j.Logger;



import org.jboss.netty.channel.Channel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yongboy.socketio.server.IOHandlerAbs;
import com.yongboy.socketio.server.transport.IOClient;

public class SendCommandHandler extends IOHandlerAbs {
	private Logger log = Logger.getLogger(this.getClass());
	
	public void OnConnect(IOClient client) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		log.debug("A user connected :: "+client.getSessionID());
	}

	
	public void OnDisconnect(IOClient client) {
		// TODO Auto-generated method stub
		log.debug("A user disconnected :: " + client.getSessionID());
	}

	
	public void OnMessage(IOClient client, String oriMessage) {
		// TODO Auto-generated method stub
		String jsonString = oriMessage.substring(oriMessage.indexOf('{'));
		jsonString = jsonString.replaceAll("\\\\", "");
		JSONObject jsonObject = JSON.parseObject(jsonString);
		String eventName = jsonObject.get("name").toString();
		if (eventName.equals("SendCommand")) {
			 JSONArray argsArray = jsonObject.getJSONArray("args");
             String command = argsArray.getString(0);
             JSONObject commaObject = JSON.parseObject(command);
             String cmdId = commaObject.getString("cmdId");
             String sensorAddr = commaObject.getString("senSorAddr");
             String arg0 = commaObject.getString("arg0");
             String storageId = commaObject.getString("storageId");
             
             SensorCommand sensorCommand = new SensorCommand();
             sensorCommand.setArg0(arg0);
             sensorCommand.setCmdId(cmdId);
             sensorCommand.setSensorAddr(sensorAddr);
             sensorCommand.setSensorHead("0x");
             
             String cmd = "0x"+sensorAddr+","+cmdId+","+arg0+",";  //当命令未正确响应时，请检查命令是否过长
     		 System.out.println("test cmd:"+cmd);
             Channel storageChannel = ClientManager.getSocketChannelByStorageId(storageId);
             storageChannel.write(sensorCommand);
             
            /* OutputStream osToSensor = null;
			try {
				//向协调器发送命令
				//osToSensor = StartDataCenter.comRead.getsPort().getOutputStream();
				byte[] b = cmd.getBytes();
	     		osToSensor.write(b);
	     		osToSensor.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
     		 
		}
	}

	
	public void OnShutdown() {
		// TODO Auto-generated method stub

	}

}
