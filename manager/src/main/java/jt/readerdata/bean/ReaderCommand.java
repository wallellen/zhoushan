package jt.readerdata.bean;

import java.io.Serializable;
import java.util.Map;

public class ReaderCommand implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int OPEN = 1;
	public static final int CLOSSE = 2;
	public static final int QUERYTAG = 3;
	public static final int GETINFO = 4;
	public static final int SETMODE = 5;
	public static final int CHECKOPEN = 6;
	
	private int commandId;
	private Map<String,String> content ;
	//socket io 端口 sessionId
	private String fromSessionID;
	
	public ReaderCommand( int id , String sessionId ) {
		this.commandId = id;
		this.fromSessionID = sessionId ;
	}

	public Map<String, String> getContent() {
		return content;
	}

	public void setContent(Map<String, String> content) {
		this.content = content;
	}

	public int getCommandId() {
		return commandId;
	}

	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}
	
	public String getFromSessionID() {
		return fromSessionID;
	}

	public void setFromSessionID(String fromSessionID) {
		this.fromSessionID = fromSessionID;
	}


	
	public ReaderCommand(  int cmdId , Map<String,String> obj){
		this.commandId = cmdId ;
		this.content = obj ;
	}

}
