package jt.sensordata.main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelLocal;

import jt.sensordata.bean.GlobalVariable;

import com.alibaba.fastjson.JSON;
import com.jtang.model.Message;
import com.jtang.service.IMessageService;
import com.yongboy.socketio.server.transport.IOClient;

/**
 * 
 * @author Administartor
 *This class provides some function which can write data to browser client
 */
public class ClientManager {
	public static IMessageService messageService;
	
	public static void sendTopToAllClient(String storageId) {
		// TODO Auto-generated method stub
		CopyOnWriteArrayList<IOClient> clientList = GlobalVariable.topClientList.get(storageId);
		if(clientList != null){
			HashMap<String,Object> sendMessage = new HashMap<String,Object>();
	    	//String content = "5:::{\"name\":\"my\",\"args\":[{\"my\":\"fffff\"}]}";
			//client.send(content);
	    	sendMessage.put("name","refresh");
	    	ConcurrentHashMap<String,ConcurrentHashMap<String,String>> networkInfo = 
	    			GlobalVariable.networkInfo.get(storageId);
	    	sendMessage.put("args", networkInfo == null?"":networkInfo);
	    	
	    	for(int i=0;i<clientList.size();i++){
	    		IOClient temp = clientList.get(i);
	    		temp.send("5:::"+JSON.toJSONString(sendMessage));
	    	}
		}
	}
	
	/**
	 * send a network info to a client
	 * @param commandName 
	 */
	public static void sendTopToAClient(IOClient client, String storageId){
		HashMap<String,Object> sendMessage = new HashMap<String,Object>();
    	//String content = "5:::{\"name\":\"my\",\"args\":[{\"my\":\"fffff\"}]}";
		//client.send(content);
    	sendMessage.put("name","refresh");
   
    	ConcurrentHashMap<String,ConcurrentHashMap<String,String>> networkInfo = 
    			GlobalVariable.networkInfo.get(storageId);
    	sendMessage.put("args", networkInfo == null?"":networkInfo);
    	System.out.println(JSON.toJSONString(sendMessage));
    	client.send("5:::"+JSON.toJSONString(sendMessage));
	}
	
	/**
	 * remove a client from the client list
	 * @param client
	 * @param flag : if flag == 1 : removce client from topclientlist
	 * 				 if flag == 2 : remove client from rttclientlist
	 */
	public static void removeClient(IOClient client,int flag){
		ConcurrentHashMap<String, CopyOnWriteArrayList<IOClient>> clientList = null;
		if(flag == 1){
			clientList = GlobalVariable.topClientList;
		}
		else if(flag == 2){
			clientList = GlobalVariable.rttClientList;
		}
		else{
			return;
		}
		
		Iterator<?> it = clientList.entrySet().iterator();
		while(it.hasNext()){
			@SuppressWarnings("unchecked")
			Map.Entry<String, CopyOnWriteArrayList<IOClient>>
				entry = (Entry<String, CopyOnWriteArrayList<IOClient>>) it.next();
			CopyOnWriteArrayList<IOClient> tempList = entry.getValue();
			if(tempList.contains(client)){
				tempList.remove(client);
				break;
			}
		}

		
	}
	
	/**
	 * 
	 * @param client
	 * @param key  Add the client to which list
	 * @param flag if flag == 1 : add  client to topclientlist,
	 * 			   if flag == 2 : add client to rttclientlist
	 */
	public static void addClientToList(IOClient client,String key,int flag){
		if(key == null || key.equals("")){
			return;
		}
			
		ConcurrentHashMap<String, CopyOnWriteArrayList<IOClient>> clientList = null;
		if(flag == 1){
			clientList = GlobalVariable.topClientList;
		}
		else if(flag == 2){
			clientList = GlobalVariable.rttClientList;
		}
		else{
			return;
		}

		CopyOnWriteArrayList<IOClient> subClientList = clientList.get(key);
		if(subClientList == null){
			subClientList = new CopyOnWriteArrayList<IOClient>();
			clientList.put(key, subClientList);
		}
		if(!clientList.contains(client)){
			subClientList.add(client);
		}
		 
	}
	
	
	public static void sendTempToAllClient(String extAddr,String time,String temperature){
    	HashMap<String,Object> sendMessage = new HashMap<String,Object>();
    	//String content = "5:::{\"name\":\"my\",\"args\":[{\"my\":\"fffff\"}]}";
		//client.send(content);
    	sendMessage.put("name","temperature");
    	HashMap<String,String> tat =  new HashMap<String,String>();
    	tat.put("time", time);
    	tat.put("temp", temperature);
    	sendMessage.put("args", tat);
    	
    	CopyOnWriteArrayList<IOClient> subClientList = GlobalVariable.rttClientList.get(extAddr);
    	if(subClientList!=null){
			 for(int i=0;i<subClientList.size();i++){
				 IOClient tempClient = subClientList.get(i);
				 tempClient.send("5:::"+JSON.toJSONString(sendMessage));
			 }
		 }
    	
    }
	
	public static Channel getSocketChannelByStorageId(String storageId){
		ChannelLocal<String> channelList = GlobalVariable.storageChannelList;
		Iterator<?> it = channelList.iterator();
		while(it.hasNext()){
			@SuppressWarnings("unchecked")
			Entry<Channel,String> entry = (Entry<Channel, String>) it.next();
			if(entry.getValue().equals(storageId)){
				return entry.getKey();
			}
		}
		return null;
	}
	
	public static void updateSysMessageRelation(String userId, IOClient client) {
		if(userId == null) {
			Iterator<Entry<String, IOClient>> it = GlobalVariable.sysMessageUserToClient.entrySet().iterator();
			while(it.hasNext()) {
				Entry<String, IOClient> temp = it.next();
				if(temp.getValue().equals(client)) {
					it.remove();
					break;
				}
			}
			return;
		}
		GlobalVariable.sysMessageUserToClient.put(userId, client);
	}
	
	/**
	 * 发送消息to somebody
	 * flag标注是否需要持久化到数据库，true需要，false不需要
	 * @param to
	 * @param msg
	 * @param flag
	 */
	public static void sendMessageToPerson(String to, Object msg,boolean flag) {
		HashMap<String,Object> sendMessage = new HashMap<String,Object>();
		sendMessage.put("name","pushMsg");
    	sendMessage.put("args", msg);
    	IOClient client = GlobalVariable.sysMessageUserToClient.get(to);
    	if(client != null) {
    		client.send("5:::"+JSON.toJSONString(sendMessage));
    	}
    	if(flag){
    		messageService.addMsg((Message)msg);
    	}
	}

	/**
	 * @return the messageService
	 */
	public static IMessageService getMessageService() {
		return messageService;
	}

	/**
	 * @param messageService the messageService to set
	 */
	public static void setMessageService(IMessageService messageService) {
		ClientManager.messageService = messageService;
	}
	
	
}
