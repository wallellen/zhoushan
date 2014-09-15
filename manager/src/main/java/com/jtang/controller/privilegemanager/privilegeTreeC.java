package com.jtang.controller.privilegemanager;

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
import com.jtang.controller.common.ControllerParent;
import com.jtang.model.Privilege;
import com.jtang.service.IPrivilegeService;

public class privilegeTreeC extends ControllerParent implements Controller {

	private IPrivilegeService privilegeService;
	
	public IPrivilegeService getPrivilegeService() {
		return privilegeService;
	}

	public void setPrivilegeService(IPrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if(action == null || action.trim().equals("")){
			return handleDefaultRequest(request);
		}else{
			action = action.trim().toLowerCase();
			switch (action) {
				case "get_all":
					return handleGetAllRequest(request, response);
				default:
					return handleDefaultRequest(request);
			}
		}
	}
	
	private ModelAndView handleGetAllRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("tree", getTree());
		
		PrintWriter  out = response.getWriter();
		out.write(JSON.toJSONString(res));
		out.flush();
		out.close();
		
		return null;
	}
	
	private Map<String,Object> newNode(Privilege nodePrivilege)
	{
		Map<String,Object> node = new HashMap<String,Object>();
		node.put("privilege", nodePrivilege);
		node.put("children", new ArrayList<Map<String,Object>>());
		
		return node;
	}
	
	private List<Privilege> getChildrenPrivileges(Privilege parent, List<Privilege> allList)
	{
		List<Privilege> children = new ArrayList<Privilege>();
		
		for (Privilege pri : allList) 
		{
			if (pri.getParent().equals(parent.getName()))
			{
				//add it into the return list
				children.add(pri);
			}
		}
		
		//delete it form the allList
		allList.removeAll(children);
		
		return children;
	}
	
	private void buildTree(Map<String, Object> node, List<Privilege> allList)
	{
		List<Map<String,Object>> children = (List<Map<String,Object>>) node.get("children");
		for (Map<String,Object> childNode : children) 
		{
			Privilege curParent = (Privilege) childNode.get("privilege");
			List<Privilege> nextChildren = getChildrenPrivileges(curParent, allList);
			if( nextChildren.size() == 0)
			{
				return ;
			}
			ArrayList<Map<String,Object>> curChildren = (ArrayList<Map<String, Object>>) childNode.get("children");
			for (Privilege pri : nextChildren)
			{
				Map<String,Object> nextChildNode = newNode(pri);
				curChildren.add(nextChildNode);
			}
			//continue to build this tree from this childNode
			buildTree(childNode, allList);
		}
	}
	
	private Map<String, Object> getTree()
	{
		List<Privilege> allList = privilegeService.getTreeprivilege();
		
		Privilege adminPrivilege = new Privilege();
		adminPrivilege.setComment("admin privilege");
		adminPrivilege.setLevel(0);
		adminPrivilege.setName("admin");
		adminPrivilege.setParent("null");
		
		Map<String, Object> head = newNode(null);
		ArrayList<Map<String,Object>> returnHead = (ArrayList<Map<String, Object>>) head.get("children");
		returnHead.add(newNode(adminPrivilege));
		buildTree(head, allList);
		
		return returnHead.get(0);
	}

	private ModelAndView handleDefaultRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		ModelAndView mv = null;
		//刷新页面
		mv = new ModelAndView("dashboard");
		String[] locations=request.getServletPath().replaceAll(".html", "").split("/");
		mv.addObject("locations", locations);
		mv.addObject("location_info", "企业管理");
		mv.addObject("content_page", "/WEB-INF/view/privilegeTreeFull.vm");			
		
		setStorageList(request, mv);
		setWorkStorage(request, mv);
		setUser(request, mv);
		setUserPosition(request, mv);
		
		return mv;
	}

}
