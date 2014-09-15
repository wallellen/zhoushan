package com.jtang.service;

import java.util.List;

import com.jtang.model.Enterprise;
import com.jtang.model.PageInfo;
import com.jtang.model.User;

public interface IUserService {
	public int registerUser();
	public User loginUser(String id,String passwd);
	
	/**
	 * 根据session中 ,当前登录的用户id为关键字
	 * @return
	 */
	public String [] getStorageListByUser(String userId);
	public List<User> getAllUser(String enterpriseId);
	
	public PageInfo<User> getAllEnterprisesByPage(PageInfo<User> pageInfo, String enterpriseId); 
}
