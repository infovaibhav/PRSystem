/**
 * 
 */
package org.iry.dto;

import java.util.List;

/**
 * @author vaibhavp
 *
 */
public class ResponseDto implements BaseDto {
	
	private static final long serialVersionUID = -2911507348308043463L;
	
	private int page = 0;
	private int total = 0;
	private int records = 0;
	@SuppressWarnings("rawtypes")
	private List rows = null;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
	}
	@SuppressWarnings("rawtypes")
	public List getRows() {
		return rows;
	}
	@SuppressWarnings("rawtypes")
	public void setRows(List rows) {
		this.rows = rows;
	}
}
