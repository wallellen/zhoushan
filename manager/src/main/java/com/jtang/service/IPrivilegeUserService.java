package com.jtang.service;

import java.util.Map;

/**
 * 
 * @author Administartor
 *this class provide service for the table:privilege_user
 */
public interface IPrivilegeUserService {
	public boolean hasPrivilege(String privilegeName,String privilegeUser);
	/**
	 * 获取指定user的可使用权限
	 * @param userId
	 * @return
	 *   Map<String,Object>
	 *        {priname:[your_privilege_names],
	 *         parent:[which_system]}
	 */
	public Map<String,Object> getPersonPrivilege(String userId);
}
