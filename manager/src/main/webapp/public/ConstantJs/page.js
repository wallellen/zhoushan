/**
 * 如何使用分页
 * 前端需要1个div和1个select：
 * 1：<div class="row-fluid" id="xxxxx"></div> ,该div的Id作为createPageInfo的第一个参数
 * 2：<select size="1" name="sample_1_length" id="xxxxx"></select>该select参数作为createPageInfo的第三个参数
 * 详细样式见rawmaterialManager.vm的138行。表格使用DT_Table
 * javascript调用样例：createPageInfo("rawmaterial_table_id",pageInfo,"raw_records_pers_page","$!request.getContextPath()/Traceability/rawmaterialManager");
 * 
 * JS引入：
 * 			<script src="$!request.getContextPath()/public/js/jquery-1.9.1.min.js"></script>
 			<script src="$!request.getContextPath()/public/ConstantJs/submitForm.js"></script>
			<script src="$!request.getContextPath()/public/ConstantJs/page.js"></script>
	CSS引入：
			<link href="$!request.getContextPath()/public/data-tables/DT_bootstrap.css" rel="stylesheet" />
 * 
 * 
 * 后台相关逻辑实现：
 * 		controller实现参见RawMaterialManagerC 132-134行
 * Dao层实现：
 * 		参见RawmaterialServiceImpl中78-82行
 * @returns
 */

String.prototype.format = function()
{
    var args = arguments;
    return this.replace(/\{(\d+)\}/g,                
        function(m,i){
            return args[i];
        });
};

jQuery.extend({form1:function(){alert(1);}});
//第一次参数表示显示第一页，第二页等的divId，第二个表示pageInfo对象，第三个表示选择每页显示多少记录数的select id
//第四个参数表示点击下一页的处理controller的URL
//第五个参数是你先要筛选数据的条件，可为空.以json为格式，比如{status:0,name:"zff"},这两条数据当选择上一页下一页或者更改每页显示数量时会传到服务器上。
function createPageInfo(divId,pageInfo,selectId,actionUrl,data){
	var pageTip = "<div class='span6'><div class='dataTables_info' id='"+divId+"_page_tip'>当前第{0}页，共{1}页</div></div>";
    var pageContent = "<div class='span6'><div class='dataTables_paginate paging_bootstrap pagination'><ul id='"+divId+"_page_content'></ul></div></div>";
	var pageLi = "<li class='"+divId+"_page_li {0}' goPage='{1}'><a href='#'>{2}</a></li>";
	
	jQuery("#"+divId).append(pageTip.format(pageInfo.currentPage,pageInfo.pageNum));
	jQuery("#"+divId).append(pageContent);
	if(pageInfo.currentPage == 1)
		jQuery("#"+divId+"_page_content").append(pageLi.format("prev disabled","0","← 上一页"));
	else
		jQuery("#"+divId+"_page_content").append(pageLi.format("prev",pageInfo.currentPage-1,"← 上一页"));
	var indexStart = 1;
	var indexEnd = 5;
	if(pageInfo.pageNum <= 5) {
		indexStart = 1;
		indexEnd = pageInfo.pageNum;
	}else if(pageInfo.pageNum - pageInfo.currentPage >= 2 && pageInfo.currentPage - 1 >= 2){
		indexStart = pageInfo.currentPage - 2;
		indexEnd = pageInfo.currentPage + 2;
	}else if(pageInfo.pageNum - pageInfo.currentPage >= 2 && pageInfo.currentPage - 1 < 2){
		indexStart = 1;
		if(pageInfo.currentPage == 1) indexEnd = pageInfo.currentPage + 4;
		else indexEnd = pageInfo.currentPage + 3;
	}else if(pageInfo.pageNum - pageInfo.currentPage < 2 && pageInfo.currentPage - 1 >= 2){
		indexEnd = pageInfo.currentPage; 
		if(pageInfo.pageNum == pageInfo.currentPage) indexStart = pageInfo.currentPage - 4;
		else indexStart = pageInfo.currentPage - 3;
	}
	
	for(var i = indexStart; i <= indexEnd; i++){
		jQuery("#"+divId+"_page_content").append(pageLi.format(i==pageInfo.currentPage?"active disabled":"",i,i));
	}
	
	if(pageInfo.pageNum == pageInfo.currentPage) jQuery("#"+divId+"_page_info").append(pageLi.format("next disabled","0","← 下一页"));
	else jQuery("#"+divId+"_page_content").append(pageLi.format("next",pageInfo.currentPage + 1,"下一页 →"));
	
	//设置当前每页显示的记录数
	jQuery("#"+selectId).val(pageInfo.recordsPP);
	//注册切换页面事件
	jQuery("."+divId+"_page_li:not(.disabled)").click(function(){
    	data = data || {};
    	data.currentPage = jQuery(this).attr("goPage");
    	data.recordsPP = jQuery("#"+selectId).find("option:selected").val();
    	data.pageNum = pageInfo.pageNum;
    	data.totalCount = pageInfo.totalCount;

    	jQuery.form(actionUrl,data,"POST").submit();
	});
	//注册修改每页显示记录数
	jQuery("#"+selectId).change(function(){
    	data = data || {};
    	data.currentPage = "1";
    	data.recordsPP = jQuery(this).find("option:selected").val();
    	jQuery.form(actionUrl,data,"POST").submit();
	});
};