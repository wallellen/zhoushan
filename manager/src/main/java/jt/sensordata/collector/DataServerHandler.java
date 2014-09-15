package jt.sensordata.collector;


import jt.sensordata.bean.GlobalVariable;
import jt.sensordata.bean.OriginalTemp;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class DataServerHandler extends SimpleChannelHandler  {
	
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
        OriginalTemp temperature = (OriginalTemp) e.getMessage();
        GlobalVariable.storageChannelList.setIfAbsent(e.getChannel(), temperature.getStorageId());
        // 打印看看是不是我们刚才传过来的那个
        GlobalVariable.dataQueue.offer(temperature);
        //System.out.println(temperature);
        
    }
	
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e){
		GlobalVariable.storageChannelList.remove(ctx.getChannel());
	}
	
}
