package com.jtang.service;

import java.util.List;

import com.jtang.model.Inventory;

public interface IInventoryService {
	public List<Inventory> getAllPro(int storageId);
	public int addProCount(String name,int addCount);
	public int minusProCount(String name,int minusCount);
	public Inventory getPro(String name);
	public int updatePro(int storageId,String name,String msg);
	public int deletePro(String name);
	public int addPro(Inventory in);
}
