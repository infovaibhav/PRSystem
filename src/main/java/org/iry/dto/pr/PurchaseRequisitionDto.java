/**
 * 
 */
package org.iry.dto.pr;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

/**
 * @author vaibhavp
 *
 */
public class PurchaseRequisitionDto implements Serializable {
	
	private static final long serialVersionUID = 1749793282106208706L;
	
	private String prNo;
	private String projectName;
	private String projectCode;
	private String rev;
	private String status;
	private String createdDateStr;
	private Long createdBy;
	private String createdByName;
	private String assignedDateStr;
	private Long assignedTo;
	private String assignedToName;
	private String authorizedDateStr;
	private Long authorizedBy;
	private String authorizedByName;
	private String approvedDateStr;
	private Long approvedBy;
	private String approvedByName;
	private String lastUpdatedDateStr;
	private Long lastUpdatedBy;
	private String lastUpdatedByName;
	private String prNoPrefix;
	private String action;
	private List<PurchaseRequisitionItemsDto> purchaseRequisionItems = new ArrayList<PurchaseRequisitionItemsDto>();
	
	private transient SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	public PurchaseRequisitionDto() {
		sdf = new SimpleDateFormat("");
		sdf.setTimeZone(new SimpleTimeZone(0,""));
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
	public String getRev() {
		return rev;
	}
	public void setRev(String rev) {
		this.rev = rev;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedDateStr() {
		return createdDateStr;
	}
	public Date getCreatedDate() throws ParseException {
		if( createdDateStr == null ) {
			return null;
		}
		return sdf.parse(createdDateStr);
	}
	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}
	public void setCreatedDateStr(Date createdDate) {
		if( createdDate != null ) {
			this.createdDateStr = sdf.format(createdDate);
		}
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public String getAssignedDateStr() {
		return assignedDateStr;
	}
	public Date getAssignedDate() throws ParseException {
		if( assignedDateStr == null ) {
			return null;
		}
		return sdf.parse(assignedDateStr);
	}
	public void setAssignedDateStr(String assignedDateStr) {
		this.assignedDateStr = assignedDateStr;
	}
	public void setAssignedDateStr(Date assignedDate) {
		if( assignedDate != null ) {
			this.assignedDateStr = sdf.format(assignedDate);
		}
	}
	public Long getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(Long assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getAssignedToName() {
		return assignedToName;
	}
	public void setAssignedToName(String assignedToName) {
		this.assignedToName = assignedToName;
	}
	public String getAuthorizedDateStr() {
		return authorizedDateStr;
	}
	public Date getAuthorizedDate() throws ParseException {
		if( authorizedDateStr == null ) {
			return null;
		}
		return sdf.parse(authorizedDateStr);
	}
	public void setAuthorizedDateStr(String authorizedDateStr) {
		this.authorizedDateStr = authorizedDateStr;
	}
	public void setAuthorizedDateStr(Date authorizedDate) {
		if( authorizedDate != null ) {
			this.authorizedDateStr = sdf.format(authorizedDate);
		}
	}
	public Long getAuthorizedBy() {
		return authorizedBy;
	}
	public void setAuthorizedBy(Long authorizedBy) {
		this.authorizedBy = authorizedBy;
	}
	public String getAuthorizedByName() {
		return authorizedByName;
	}
	public void setAuthorizedByName(String authorizedByName) {
		this.authorizedByName = authorizedByName;
	}
	public String getApprovedDateStr() {
		return approvedDateStr;
	}
	public Date getApprovedDate() throws ParseException {
		if( approvedDateStr == null) {
			return null;
		}
		return sdf.parse(approvedDateStr);
	}
	public void setApprovedDateStr(String approvedDateStr) {
		this.approvedDateStr = approvedDateStr;
	}
	public void setApprovedDateStr(Date approvedDate) {
		if( approvedDate != null ) {
			this.approvedDateStr = sdf.format(approvedDate);
		}
	}
	public Long getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getApprovedByName() {
		return approvedByName;
	}
	public void setApprovedByName(String approvedByName) {
		this.approvedByName = approvedByName;
	}
	public String getLastUpdatedDateStr() {
		return lastUpdatedDateStr;
	}
	public Date getLastUpdatedDate() throws ParseException {
		if( lastUpdatedDateStr == null ) {
			return null;
		}
		return sdf.parse(lastUpdatedDateStr);
	}
	public void setLastUpdatedDateStr(String lastUpdatedDate) {
		if( lastUpdatedDateStr != null ) {
			this.lastUpdatedDateStr = sdf.format(lastUpdatedDate);			
		}
	}
	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getLastUpdatedByName() {
		return lastUpdatedByName;
	}
	public void setLastUpdatedByName(String lastUpdatedByName) {
		this.lastUpdatedByName = lastUpdatedByName;
	}
	public String getPrNoPrefix() {
		return prNoPrefix;
	}
	public void setPrNoPrefix(String prNoPrefix) {
		this.prNoPrefix = prNoPrefix;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public void addPurchaseRequisionItems(PurchaseRequisitionItemsDto purchaseRequisitionItemDto) {
		purchaseRequisionItems.add(purchaseRequisitionItemDto);
	}
	public List<PurchaseRequisitionItemsDto> getPurchaseRequisionItems() {
		return purchaseRequisionItems;
	}
	public void setPurchaseRequisionItems(List<PurchaseRequisitionItemsDto> purchaseRequisionItems) {
		this.purchaseRequisionItems = purchaseRequisionItems;
	}
}
