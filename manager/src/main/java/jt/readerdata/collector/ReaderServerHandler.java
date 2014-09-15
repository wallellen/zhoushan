package jt.readerdata.collector;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import jt.readerdata.bean.ReaderCommand;
import jt.readerdata.bean.ShareDatas;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class ReaderServerHandler extends SimpleChannelHandler {

	//socket.io cmdReader
	private ReaderCommandHandler readerCmdHander;
 
	public void setReaderCmdHander(ReaderCommandHandler readerCmdHander) {
		this.readerCmdHander = readerCmdHander;
	}


	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e){
		
		HashMap<String,Object> msg = (HashMap<String, Object>) e.getMessage();

		if( msg != null && msg.get("name") == null){
			//更新channellis
			ShareDatas.storageChannelList.setIfAbsent(e.getChannel(), msg.get("storageId")+"");
		}
		
		else handleRrMsg(msg);
		
	}


	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e){
		ShareDatas.storageChannelList.remove(ctx.getChannel());
	}
	
	/**
	 * 发送命令
	 * @param commId
	 * @param storageId
	 */
	public void sendCommand ( ReaderCommand cmd , int storageId) {

		Channel channel = null;
		
		Iterator<Entry<Channel, String>> it = ShareDatas.storageChannelList.iterator();
		while( it.hasNext() ){
			Entry<Channel, String> i = it.next();
			if( Integer.parseInt(i.getValue()) == storageId )
				channel = i.getKey();
		}
		
		//channel 写命令
		if( channel != null) channel.write(cmd);
	
	}
    
	private void handleRrMsg( HashMap<String,Object> msg ) {
		if( msg == null ) return;
		readerCmdHander.sendBackInfo(msg);
		
	}
}
