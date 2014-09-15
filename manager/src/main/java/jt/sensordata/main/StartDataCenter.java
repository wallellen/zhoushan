package jt.sensordata.main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jtang.service.IMessageService;
import com.yongboy.socketio.MainServer;

import jt.sensordata.collector.DataCollectorServer;
import jt.sensordata.consumer.DataConsumer;
import jt.sensordata.consumer.DataSaver;
import jt.sensordata.server.RTTempDataHandler;
import jt.sensordata.server.SendCommandHandler;
import jt.sensordata.server.SystemMessageHandler;
import jt.sensordata.server.TopDataHandler;

public class StartDataCenter {
	

	public static int RTTIOPort = 9001;
	public static int TopIOPort = 9002;
	public static int CommandPort = 9003;
	public static int SysMessagePort = 9004;
	public static String comNumber = "";

	public DataSaver dataSaver; //spring will initial this variable
	public IMessageService messageService;
	

	/**
	 * @return the messageService
	 */
	public IMessageService getMessageService() {
		return messageService;
	}

	/**
	 * @param messageService the messageService to set
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * @return the dataSaver
	 */
	public DataSaver getDataSaver() {
		return dataSaver;
	}

	/**
	 * @param dataSaver the dataSaver to set
	 */
	public void setDataSaver(DataSaver dataSaver) {
		this.dataSaver = dataSaver;
	}

	public  void startAll(String [] args){	
		startDataCollector();
		startDataConsumer();
		startCommandSender();
		startRTTSocket();
		startTopSocket();
		startSysMessageServer();
		//this if for check zigbee , store temp,update sensor work....
		dataSaver.startAllSaver();
	}
	
	private void startSysMessageServer() {
		// TODO Auto-generated method stub
		MainServer sysMsg = new MainServer(new SystemMessageHandler(messageService), SysMessagePort);
		sysMsg.start();
	}

	private void startDataConsumer() {
		// TODO Auto-generated method stub
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i = 0; i < 10; i++){
			exec.execute(new DataConsumer());
		}
		exec.shutdown();
	}

	private void startDataCollector() {
		// TODO Auto-generated method stub
		new DataCollectorServer().startServer();
	}

	private static void startCommandSender() {
		// TODO Auto-generated method stub
		MainServer CSServer = new MainServer(new SendCommandHandler(), CommandPort);
		CSServer.start();
	}

	
	/**
	 * 用于推送最新采集到的传感器数据
	 */
	public static void startRTTSocket(){
		//RTT = Real Time Temperature
		MainServer RTTServer = new MainServer(new RTTempDataHandler(),RTTIOPort);
		RTTServer.start();
	}
	

	/**
	 * 该Socket接口用于推送最新的拓扑结构图
	 */
	public static void startTopSocket(){
		MainServer CYTServer = new MainServer(new TopDataHandler(),TopIOPort);
		
		CYTServer.start();
	}
}
