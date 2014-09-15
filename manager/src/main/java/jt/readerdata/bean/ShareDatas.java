package jt.readerdata.bean;

import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.channel.ChannelLocal;

import com.yongboy.socketio.server.transport.IOClient;

public class ShareDatas {

    //保存所有活跃的socket连接，一级仓库storgeid为key,二级socket sessionId为key
	public static Map<String,Map<String,IOClient>> ioCmdList = new HashMap<String,Map<String,IOClient>>();
	
	//every storage will connect to datacollectorserver,so every storage will have a socketchannel
	public static ChannelLocal<String> storageChannelList = new ChannelLocal<String>(); //string is the storageid
}
