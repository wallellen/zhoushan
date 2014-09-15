package com.jtang.controller.extra;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.jtang.model.Privilege;
import com.jtang.model.User;
import com.jtang.service.IPrivilegeService;
import com.jtang.service.IPrivilegeUserService;

/**
 * 处理对权限详情的请求
 * @author yyj
 *
 */
public class PrivilegeC implements Controller {
	
	private IPrivilegeService privilegeService;
	//privilege_user
	public IPrivilegeUserService pus;

	public IPrivilegeUserService getPus() {
		return pus;
	}

	public void setPus(IPrivilegeUserService pus) {
		this.pus = pus;
	}

	public IPrivilegeService getPrivilegeService() {
		return privilegeService;
	}

	public void setPrivilegeService(IPrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		getPrivilege(request,response);
		return null;
	}
	
	private void  getPrivilege(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		PrintWriter  out = response.getWriter();
		String result = null;
		
		String action = request.getParameter("action");
		
		if ( action.equals("getAll") ){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("tree", getTree());
			result = JSON.toJSONString(res);	
		}
		else if( action.equals("getOne")){
			User user = (User) request.getSession().getAttribute("user");
			if( user != null ) {
				Map<String,Object> res = new HashMap<String,Object>();
				res.put("tree", getTree());
				res.put("user", pus.getPersonPrivilege(user.getId()));
				result = JSON.toJSONString(res);
			}
		}
		
		if( result == null ) return ;
		
		out.write(result);
		out.flush();
		out.close();
		
	}
	
	
	/**
	 * 
	 * @return
	 * {
	 *   name:xx
	 *   childern:[{name:xx,comment:xx,childer:[...]}]
	 * }
	 */
	
	private HashMap<String,Object> PrivilegeToMap(Privilege pri)
	{
		HashMap<String,Object> child = new HashMap<String, Object>();
		child.put("name", pri.getName());
		child.put("level", pri.getLevel());
		child.put("children", null);
		
		return child;
	}
	
	@SuppressWarnings("unchecked")
	private void addChildToParentArray(List<Privilege> pris, Map<String,Object> parent)
	{
		if(parent == null) return;
		
		if(parent.get("children") == null)
		{
			parent.put("childre", new ArrayList<Map<String,Object>>());
		}
		for(Privilege pri : pris)
		{
			((ArrayList<Map<String,Object>>)parent.get("children")).add(PrivilegeToMap(pri));
		}	
	}
	
	private List<Privilege> getCurrentChildren(List<Privilege> all, String parent){
		
		List<Privilege> pris = new ArrayList<Privilege>();
		int len = all.size();
		for( int i =0 ; i < len ; i++ )
		{
			if(all.get(i).getParent() == parent)
			{
				pris.add(all.get(i));
			}
		}
		all.removeAll(pris);
		return pris;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String,Object> getTree(){
		
		int deep = 4;
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("name", "admin");
		map.put("children", new ArrayList<Map<String,Object>>());
		List<Privilege> allList = privilegeService.getTreeprivilege();
		
		HashMap<String,Object> parent = map;
		for(int i = 0 ; i < deep && allList.size() > 0; i++)
		{
			if(i==0)
			{
				List<Privilege> pris = getCurrentChildren(allList,"admin");
				addChildToParentArray(pris,parent);
			}
			else
			{
				
			}
			
		}
		
		//HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("name", "all");
		map.put("comment", "全部权限");		
		List<Map<String,Object>> allChild = new ArrayList<Map<String,Object>>();
		for( int i =0 ; i < 3 ; i++ ){
			Map<String,Object> j =  new HashMap<String,Object>();
			j.put("name", i+"");
			j.put("comment", i+"层权限");
			j.put("children", new ArrayList<Privilege>());
			
			allChild.add(j);
		}
		map.put("children", allChild);
		
		List<Privilege> list = privilegeService.getTreeprivilege();
		for( Privilege pri : list){
			((ArrayList<Privilege>) allChild.get(pri.getLevel() - 1).get("children")).add(pri);
		}
		
		return map;
	}

}
