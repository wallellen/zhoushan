package com.jtang.service.impl;

import java.sql.Types;
import java.util.List;

import com.jtang.dao.BasicManagerDao;
import com.jtang.dao.DaoUtil;
import com.jtang.dao.sqlUtil.Zql;
import com.jtang.enums.RowMapperEnum;
import com.jtang.model.Enterprise;
import com.jtang.model.PageInfo;
import com.jtang.model.User;
import com.jtang.service.IUserService;

public class UserServiceImpl implements IUserService{
	
	private BasicManagerDao manager;

	
	public BasicManagerDao getManager() {
		return manager;
	}

	public void setManager(BasicManagerDao manager) {
		this.manager = manager;
	}

	public int registerUser() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @author yyj
	 * @param
	 * 	   id 
	 * 	   passwd  
	 * @return
	 *
	 */
	public User loginUser(String id, String passwd) {
		// TODO Auto-generated method stub

		Object [] paras=new String[2];
		paras[0] = id;
		paras[1] = passwd;
		int [] argTypes = {Types.VARCHAR,Types.VARCHAR};
		List<User> users=manager.query("select * from user where id=? and password=?",paras,argTypes,RowMapperEnum.USER.getMapperName());
		if(users.size() == 1){
			return users.get(0);
		}else{
			return null;
		}

	}

	public String[] getStorageListByUser(String userId) {
		// TODO Auto-generated method stub
		String sql = "select * from user where id=?";
		Object [] paras = {userId};
		int [] argTypes = {Types.VARCHAR};
		List<User> users=manager.query(sql,paras,argTypes,RowMapperEnum.USER.getMapperName());
		if(users.size() == 1){
			
			return users.get(0).getStorageList()==null?null:users.get(0).getStorageList().split(":");
		}else{
			return null;
		}
	}
	
		public List<User> getAllUser(String enterpriseId) {
		// TODO Auto-generated method stub
		return manager.query(Zql.selectFrom("user").
				where(Zql.condition("enterpriseId", "=", enterpriseId)), RowMapperEnum.USER.getMapperName());
		//return manager.query("select * from user where en",null,null,RowMapperEnum.USER.getMapperName());
	}

		@Override
		public PageInfo<User> getAllEnterprisesByPage(PageInfo<User> pageInfo, String enterpriseId) {
			// TODO Auto-generated method stub
			List<User> userList = manager.query(
					DaoUtil.getSelectorByPage(pageInfo, "user", Zql.condition("enterpriseId", "=", enterpriseId), manager),
					RowMapperEnum.USER.getMapperName());
			pageInfo.setDataList(userList);
			return pageInfo;
		}

}
