package jt.sensordata.bean;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import org.jboss.netty.channel.ChannelLocal;

import com.jtang.model.Sensor;
import com.yongboy.socketio.server.transport.IOClient;

public class GlobalVariable {
	
	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//the real-time temperatur data store in this queue,many consumer thread will get data from here
	public static BlockingQueue<OriginalTemp> dataQueue = new LinkedBlockingQueue<OriginalTemp>(1000);
	
	//some sensor need to be updated or added 
	public static BlockingQueue<Sensor> unHandledSensor = new LinkedBlockingQueue<Sensor>(1000);
	
	//a sensor's average temperatur stores in averageTemp<extend addr,temperatur> 
	public static ConcurrentHashMap<String, Float> averageTemp = new ConcurrentHashMap<String, Float>();
	
	//extend <"shortaddr,storageId",extend addr>
	public static ConcurrentHashMap<String, String> shortToExt = new ConcurrentHashMap<String, String>();
	
	//<extendAddr,short addr>
	public static ConcurrentHashMap<String, String> extToShort = new ConcurrentHashMap<String, String>();
	
	//networkInfo : storageId ---- (short addr-----(deviceclass，parent short addr,time))
	public static ConcurrentHashMap<String, ConcurrentHashMap<String,ConcurrentHashMap<String,String>>>
		networkInfo = new ConcurrentHashMap<String, ConcurrentHashMap<String,ConcurrentHashMap<String,String>>>();
	
	//topclientlist = <storage id,clientlist>
	public static ConcurrentHashMap<String,CopyOnWriteArrayList<IOClient>> topClientList =
			new ConcurrentHashMap<String,CopyOnWriteArrayList<IOClient>>();
	
	//rttclientlist = <extend addr,clientlist>
	public static ConcurrentHashMap<String,CopyOnWriteArrayList<IOClient>>
			rttClientList = new ConcurrentHashMap<String,CopyOnWriteArrayList<IOClient>>();
	
	public static ConcurrentHashMap<String, IOClient> sysMessageUserToClient =
			new ConcurrentHashMap<>();
	
	//every storage will connect to datacollectorserver,so every storage will have a socketchannel
	public static ChannelLocal<String> storageChannelList = new ChannelLocal<String>(); //string is the storageid
	
	//
	public static HashMap<String,String> controllerToPrivilege = new HashMap<String,String> ()
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("sensorManager","sensor_manager");
			put("storageManager","storage_manager");
			put("realTimeData","real_time_temperature");
			put("historyData","history_temperature");
			put("exceptionData","exception_history");
			put("tmDownload","monitor_mobile_app");
			put("datarecord","source_data_query");
			put("dataquery","source_data_record");
			//add warahouse 
			put("inventoryIn","inventory_in");
			put("inventoryOut","inventory_out");
			put("rfidReader","rfid_reader");
			put("inventory","inventory_show");
			put("repository","repository_show");
			//add warahouse end
		}
	};
}
