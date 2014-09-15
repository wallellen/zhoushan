package jt.readerdata.collector;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import jt.readerdata.bean.ReaderConstant;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import com.yongboy.socketio.MainServer;

public class ReaderCollectorServer {
	
	public void startServer () {
		
		//netty 服务器
		ServerBootstrap bootstrap = new ServerBootstrap (
				new NioServerSocketChannelFactory( Executors.newCachedThreadPool(),Executors.newCachedThreadPool())
				);
			
		//channel PipleLine
		final ReaderServerHandler serverHandler = new ReaderServerHandler();
		bootstrap.setPipelineFactory(new ChannelPipelineFactory () {

			public ChannelPipeline getPipeline() throws Exception {
				// TODO Auto-generated method stub
				return Channels.pipeline(
						new ObjectEncoder(),
		        		new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())),
		        		serverHandler);
			}});
		
		bootstrap.bind(new InetSocketAddress(ReaderConstant.PORT_COLLECTOR));
		
		//socketio server
		
		//socket io
		ReaderCommandHandler cmdHandler = new ReaderCommandHandler();
		new MainServer( cmdHandler , ReaderConstant.PORT_SOCKETIO_CMD ).start();
		
		//handler 设置
		serverHandler.setReaderCmdHander(cmdHandler);
		cmdHandler.setReaderHandler(serverHandler);
		
	}
}
