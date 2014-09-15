package com.jtang.uploadUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jtang.uploadUtil.PoiReadExcel;

public class Upload extends HttpServlet {

	/**
	 * @author chenminglong
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		 DiskFileItemFactory factory=new DiskFileItemFactory();
		 //设置内存保存阈值为20MB
		 factory.setSizeThreshold(1024*1024*20);
		 //设置当文件大于内存阈值时磁盘临时文件夹，上传完毕会自行消失
		 factory.setRepository(new File(this.getServletContext().getRealPath("/")));
		 ServletFileUpload upload=new ServletFileUpload(factory);
		 //设置上传文件阈值为200MB
		 upload.setSizeMax(1024*1024*200);

         List<FileItem> items=null;
         String ServerFileName=null;
         String theFileName=null;
         try {
        	//上传文件，并解析出所有的表单字段，包括普通字段和文件字段
			items = upload.parseRequest(request);
			for(Iterator<FileItem> it =items.iterator();it.hasNext();){
				FileItem item=(FileItem)it.next();
				if(!item.isFormField()){

					String fileName=item.getName();
					theFileName=fileName;
					//服务器端将上传的文件保存在临时文件夹
					ServerFileName=System.getProperty("java.io.tmpdir")+System.currentTimeMillis()
							+fileName.substring(fileName.lastIndexOf("."),fileName.length());
					FileOutputStream fos=new FileOutputStream(ServerFileName);
					if(item.isInMemory()){
					    fos.write(item.get());
					   	fos.close();
					}else{
					    InputStream is=item.getInputStream();
					   	byte[] buffer=new byte[1024];
					   	int len;
				    	while((len=is.read(buffer))>0){
					    	fos.write(buffer,0,len);
					    }	
					    is.close();
					    fos.close();
					    item.delete();
					}
					    
				}
				
			}
			PoiReadExcel.read(ServerFileName);
			
			//以下代码可避免上传完成出现error: 'Empty file upload result'-- jquery.fileupload-ui.js/73行的bug
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("{\"files\":[{\"name\":\""+theFileName+"\"}]}");
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("success!");
		out.flush();
		out.close();
	}
	
	public void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}
