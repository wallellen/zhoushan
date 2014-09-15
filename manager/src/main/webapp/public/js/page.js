var PageIndex = 1;
var PageCount = 0;

var PageHtml = "";
var CallBack;

function SetPage(Pindex,Pcount,PageID,CallBackFunc)
{
	PageHtml += "<div class='page'><a href='javascript:void(0);' onclick='PrePage();' id='Pre' >上一页</a><a href='javascript:void(0);' onclick='NexPage();' id='Nex'>下一页</a>";
	PageHtml += "  转至 <select style='margin-top:0px;' id='PageJump' name=''  size='1' onchange='PJump();'>";

	for (var i = 1; i <= Pcount; i++) {

		if (i == Pindex) {
			PageHtml += "<option style='height:30px;' value='" + i + "'  SELECTED>" + i + "</option>";

		} else {
			PageHtml += "<option value='"+i+"'>" + i + "</option>";
		}
	}
	PageHtml += "</select>   页<a href='javascript:void(0);' onclick='FirstPage()'>返回首页</a></div>";
	PageCount = Pcount;
	PageIndex=Pindex;

	var PageNode = document.getElementById(PageID);
	PageNode.innerHTML=PageHtml;
	CallBack = CallBackFunc;
	CallBack(PageIndex);
}

//上一页

function PrePage() {

	PageIndex--;
 
	if (PageIndex <= 0) {
		PageIndex = 1;
		return;
	}
 
	var jumpNode = document.getElementById("PageJump");
	jumpNode.value = PageIndex;
 
	CallBack(PageIndex);
}
 
// 下一页
function NexPage() {
 
	PageIndex++;
 
	if (PageIndex > PageCount) {
		PageIndex = PageCount;
		return;
	}
 
	var jumpNode = document.getElementById("PageJump");
	jumpNode.value = PageIndex;
 
	CallBack(PageIndex);
}
 
//跳到第几页
function PJump() {
 
	var jumpNode = document.getElementById("PageJump");
 
	PageIndex = parseInt(jumpNode.value);
 
	CallBack(PageIndex);
}
 
// 第一页
function FirstPage() {
 
if (PageIndex!=1)
{
	PageIndex = 1;
	var jumpNode = document.getElementById("PageJump");
	jumpNode.value = PageIndex;
 
	CallBack(PageIndex);
	}
 
}