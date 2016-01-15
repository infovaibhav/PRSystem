/**
 * 
 */
package org.iry.dto;

import java.io.Serializable;

/**
 * @author vaibhavp
 *
 */
public class SearchCriteria implements Serializable {
	
	private static final long serialVersionUID = -2937528338413447658L;
	
	private int page;
    private int pageSize;
    private String sidx;
    private String sord;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public boolean needPagination() {
		return !(page == 0 || pageSize == 0);
	}
	public int getFirstResult() {
		return (page == 0 ? 1 : (((page - 1) * pageSize) + 1));
	}
	public int getMaxResult() {
		return pageSize;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
}
