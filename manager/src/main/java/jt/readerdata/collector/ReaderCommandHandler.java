package jt.readerdata.collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import jt.readerdata.bean.ReaderCommand;
import jt.readerdata.bean.ShareDatas;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yongboy.socketio.server.IOHandlerAbs;
import com.yongboy.socketio.server.transport.IOClient;

public class ReaderCommandHandler extends IOHandlerAbs {
	
	//netty handler
	private ReaderServerHandler readerHandler;
	
	public void setReaderHandler(ReaderServerHandler readerHandler) {
		this.readerHandler = readerHandler;
	}

	private Logger log = Logger.getLogger(this.getClass());

	public void OnConnect(IOClient client) {
		// TODO Auto-generated method stub
	
		log.debug("A user connected :: SessionID"+client.getSessionID());
		
	}

	public void OnDisconnect(IOClient client) {
		// TODO Auto-generated method stub
		log.debug("A user disconnected :: " + client.getSessionID());
		
	}

	public void OnMessage(IOClient client, String msg) {
		// TODO Auto-generated method stub
		
		String jsonString = msg.substring(msg.indexOf('{'));
		jsonString = jsonString.replaceAll("\\\\", "");
		JSONObject jsonObject = JSON.parseObject(jsonString);
		String eventName = jsonObject.get("name").toString();
		
		if( eventName == null ) return;
		
		JSONObject cmdObject = JSON.parseObject(jsonObject.getJSONArray("args").getString(0));
		String storageIdString = cmdObject.getString("storageId");
		
		if( storageIdString == null)  return;
		
		if ( eventName.equals("readerCmd") ){
			//sendout cmd		
			//处理content
			//cmd.setContent((Map<String, String>) cmdObject.get("content"));
			readerHandler.sendCommand( 
					new ReaderCommand(Integer.parseInt(cmdObject.getString("cmdId")) , client.getSessionID()), 
					Integer.parseInt(storageIdString)
					);
		}
		else if( eventName.equals("register") ){
			//socket 注册
			
			if( !ShareDatas.ioCmdList.containsKey(storageIdString) )
				ShareDatas.ioCmdList.put(storageIdString, new HashMap<String,IOClient>());	
			
			ShareDatas.ioCmdList.get(storageIdString).put(client.getSessionID(), client);
			
			//检测当前仓库的Reader是否已经打开
			readerHandler.sendCommand(
					new ReaderCommand(ReaderCommand.CHECKOPEN , client.getSessionID() ), 
					Integer.parseInt(storageIdString)
					);
		}
	} 

	public void OnShutdown() {
		// TODO Auto-generated method stub
		log.debug("shutdown");
	}
	
	private void sendBack (  Map<String,Object> msg , IOClient client ){
	
		if( client != null && client.isOpen() ){
			ArrayList<Map<String,Object>> args = new ArrayList<Map<String,Object>>();
			args.add(msg);
			Map<String,Object> sendbackMsg = new HashMap<String,Object>();
			sendbackMsg.put("name", msg.get("name"));
			sendbackMsg.put("args", args);
			
			String sendBack = "5:::"+JSON.toJSONString(sendbackMsg);
			log.debug("send back:   "+sendBack);
			client.send(sendBack);
	
		}
		
		return ;
	}
	
	public void sendBackInfo ( Map<String,Object> msg ) {
		
		Integer cmdId =  (Integer) msg.get("name");
		String storageId =  msg.get("storageId") + "";
		String sessionId = (String) msg.get("sessionId");
		
		if( cmdId == null || storageId == null || sessionId == null ) return;
		
		boolean isOPenCloseSucc = cmdId == ReaderCommand.CLOSSE || cmdId == ReaderCommand.OPEN && msg.get("res").equals("0");
		
		//获取所属仓库打开的ioSocket
		Map<String,IOClient> socketList = ShareDatas.ioCmdList.get(storageId);
		Iterator<Entry<String, IOClient>> it = socketList.entrySet().iterator();
		while ( it.hasNext() ){
			Entry<String,IOClient> entry = it.next();
			if( isOPenCloseSucc ){
				//port 成功打开或者关闭 , 通告处所有的该storage活跃的socket连接
				sendBack( msg , entry.getValue() );	
			}
			else if ( entry.getKey().equals(sessionId) ){
				//其他命令的执行 后,只发送给cmd发送者
				sendBack( msg , entry.getValue() );	
			}
		}
		
	}

}
