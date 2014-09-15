package jt.sensordata.server;

import java.util.List;
import java.util.Map;

import jt.sensordata.main.ClientManager;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jtang.model.Message;
import com.jtang.service.IMessageService;
import com.yongboy.socketio.server.IOHandlerAbs;
import com.yongboy.socketio.server.transport.IOClient;

public class SystemMessageHandler extends IOHandlerAbs {

	private Logger log = Logger.getLogger(this.getClass());
	public IMessageService messageService;
	public SystemMessageHandler(IMessageService messageService) {
		// TODO Auto-generated constructor stub
		this.messageService = messageService;
	}

	@Override
	public void OnConnect(IOClient client) {
		// TODO Auto-generated method stub
		log.debug("A user connected to message server:" + client.getId());
		//do i have message?
	}

	@Override
	public void OnDisconnect(IOClient client) {
		// TODO Auto-generated method stub
		log.debug("A user disconnected :: " + client.getSessionID());
		//should remove this client from user-client map
		ClientManager.updateSysMessageRelation(null, client);
	}

	@Override
	public void OnMessage(IOClient client, String msg) {
		// TODO Auto-generated method stub
		String jsonString = msg.substring(msg.indexOf('{'));
		jsonString = jsonString.replaceAll("\\\\", "");
		JSONObject jsonObject = JSON.parseObject(jsonString);
		String eventName = jsonObject.get("name").toString();
		if(eventName.equalsIgnoreCase("whoami")){
			JSONArray argsArray = jsonObject.getJSONArray("args");
            String userId = argsArray.getString(0);
            log.debug("A user connected to message server:" + userId);
            //add this relation between userid and client
            ClientManager.updateSysMessageRelation(userId, client);
            
            List<Message> msgList = messageService.getMsgListUnRead(userId,5);
   
            for(Message message : msgList) {
            	ClientManager.sendMessageToPerson(userId, message,false);
            }
            Map<String, Object> unReadTotal = new HashedMap();
            unReadTotal.put("totalMsg", messageService.totalUnReadMsg(userId));
            ClientManager.sendMessageToPerson(userId, unReadTotal,false);
            //just for test
            /*Message msg1 = new Message();
            msg1.setFrom("System");
            msg1.setTo("zxy");
            msg1.setContent("原料xxxxx严重不足，赶紧采购");
            msg1.setTime("2014-06-15 11:36");
            msg1.setTitle("原料采购提醒");
            msg1.setMessageId("12345677");
            ClientManager.sendMessageToPerson("System", "zxy", msg1);*/
            
		}else if(eventName.equalsIgnoreCase("read")){
			
		}
	}

	@Override
	public void OnShutdown() {
		// TODO Auto-generated method stub
		log.debug("A connect shutdown....");
	}

}
