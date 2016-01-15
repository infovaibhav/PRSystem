/**
 * 
 */
package org.iry.dto.pr;

import java.util.Date;
import java.util.List;

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
	private Date fromTime = null;
	private Date toTime = null;
	private boolean exactMatch = true;
	
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
	public Date getFromTime() {
		return fromTime;
	}
	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}
	public Date getToTime() {
		return toTime;
	}
	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}
	public boolean isExactMatch() {
		return exactMatch;
	}
	public void setExactMatch(boolean exactMatch) {
		this.exactMatch = exactMatch;
	}
}
