package com.jtang.service;

import java.util.List;
import java.util.Map;

import com.jtang.model.StorageService;

public interface IStorageServiceSettingService {
	public List<StorageService> getAllStorageService();
	public int add(StorageService s);
	public int delete(int id);
	public List<Map<String, Object>> getUserCount();

}
