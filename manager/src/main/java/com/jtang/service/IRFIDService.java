package com.jtang.service;

import java.util.HashMap;
import java.util.Map;

public interface IRFIDService {
	public Map<String,Object> openPort();
	public int closePort();
	public Map<String,Object> getReaderInfo();
	public int setReadMode(int mode);
	public int setSoundMode(int mode);
	public int setMemArea(int mode);
	public byte[] getReaderMode();
	public HashMap<String,Object> queryActiveTags(int area);
}
