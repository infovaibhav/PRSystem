/**
 * 
 */
package org.iry.dto.pr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

import org.iry.dto.SearchCriteria;

/**
 * @author vaibhavp
 *
 */
public class PurchaseRequestSearchCriteria extends SearchCriteria {
	
	private static final long serialVersionUID = -5137037712670108009L;
	
	private String prNo = null;
	private String projectName = null;
	private String projectCode = null;
	private List<String> statuses = null;
	private List<Long> createdBy = null;
	private List<Long> assignedTo = null;
	private List<Long> authorizedBy = null;
	private List<Long> approvedBy = null;
	private String fromTimeStr = null;
	private String toTimeStr = null;
	private boolean exactMatch = true;

	private transient SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	
	public PurchaseRequestSearchCriteria() {
	}
	
	public String getPrNo() {
		return prNo;
	}
	public void setPrNo(String prNo) {
		this.prNo = prNo;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public List<String> getStatuses() {
		return statuses;
	}
	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}
	public List<Long> getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(List<Long> createdBy) {
		this.createdBy = createdBy;
	}
	public List<Long> getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(List<Long> assignedTo) {
		this.assignedTo = assignedTo;
	}
	public List<Long> getAuthorizedBy() {
		return authorizedBy;
	}
	public void setAuthorizedBy(List<Long> authorizedBy) {
		this.authorizedBy = authorizedBy;
	}
	public List<Long> getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(List<Long> approvedBy) {
		this.approvedBy = approvedBy;
	}
	public boolean needFromTimeRestriction() {
		if( fromTimeStr == null || fromTimeStr.trim().length() == 0 ) {
			return false;
		}
		return true;
	}
	public String getFromTimeStr() {
		return this.fromTimeStr;
	}
	public Date getFromTime() throws ParseException {
		if( fromTimeStr == null || fromTimeStr.trim().length() == 0 ) {
			return null;
		}
		return sdf.parse(fromTimeStr);
	}
	public void setFromTimeStr(String fromTimeStr) {
		this.fromTimeStr = fromTimeStr;
	}
	public void setFromTime(Date fromTime) {
		if( fromTime != null ) {
			this.fromTimeStr = sdf.format(fromTime);
		}
	}
	public boolean needToTimeRestriction() {
		if( toTimeStr == null || toTimeStr.trim().length() == 0 ) {
			return false;
		}
		return true;
	}
	public String getToTimeStr() {
		return toTimeStr;
	}
	public Date getToTime() throws ParseException {
		if( toTimeStr == null || toTimeStr.trim().length() == 0 ) {
			return null;
		}
		return sdf.parse(toTimeStr);
	}
	public void setToTimeStr(String toTimeStr) {
		this.toTimeStr = toTimeStr;
	}
	public void setToTime(Date toTime) {
		if( toTime != null ) {
			this.toTimeStr = sdf.format(toTime);
		}
	}
	public boolean isExactMatch() {
		return exactMatch;
	}
	public void setExactMatch(boolean exactMatch) {
		this.exactMatch = exactMatch;
	}
}
