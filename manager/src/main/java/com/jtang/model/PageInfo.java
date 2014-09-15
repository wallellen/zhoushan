package com.jtang.model;

import java.util.List;

public class PageInfo<T> {
	private int currentPage;
	private int recordsPP; //per page
	private int totalCount;
	private int pageNum;  //一共多少页？
	private List<T> dataList;
	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * @return the recordsPP
	 */
	public int getRecordsPP() {
		return recordsPP;
	}
	/**
	 * @param recordsPP the recordsPP to set
	 */
	public void setRecordsPP(int recordsPP) {
		this.recordsPP = recordsPP;
	}
	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}
	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * @return the pageNum
	 */
	public int getPageNum() {
		return pageNum;
	}
	/**
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	/**
	 * @return the dataList
	 */
	public List<T> getDataList() {
		return dataList;
	}
	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
	
}
