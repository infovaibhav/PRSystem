/**
 * 
 */
package org.iry.dto.pr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.iry.dto.Action;
import org.iry.dto.BaseDto;

/**
 * @author vaibhavp
 *
 */
public class PurchaseRequisitionDto implements BaseDto {
	
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
	private String acknowledgedDateStr;
	private Long acknowledgedBy;
	private String acknowledgedByName;
	private String lastUpdatedDateStr;
	private Long lastUpdatedBy;
	private String lastUpdatedByName;
	private String prNoPrefix;
	private boolean editable;
	private String prRemark;
	private List<Action> allowedStatusChanges = new ArrayList<Action>();
	private String allowedStatusChangesStr;
	private boolean submitted;
	private List<PurchaseRequisitionItemsDto> purchaseRequisionItems = new ArrayList<PurchaseRequisitionItemsDto>();

	private transient int totalRecords = 0;
	private transient SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
	
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
		if( createdDateStr == null || createdDateStr.isEmpty() ) {
			return null;
		}
		return sdf.parse(createdDateStr);
	}
	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}
	public void setCreatedDate(Date createdDate) {
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
		if( assignedDateStr == null || assignedDateStr.isEmpty() ) {
			return null;
		}
		return sdf.parse(assignedDateStr);
	}
	public void setAssignedDateStr(String assignedDateStr) {
		this.assignedDateStr = assignedDateStr;
	}
	public void setAssignedDate(Date assignedDate) {
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
		if( authorizedDateStr == null || authorizedDateStr.isEmpty() ) {
			return null;
		}
		return sdf.parse(authorizedDateStr);
	}
	public void setAuthorizedDateStr(String authorizedDateStr) {
		this.authorizedDateStr = authorizedDateStr;
	}
	public void setAuthorizedDate(Date authorizedDate) {
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
		if( approvedDateStr == null || approvedDateStr.isEmpty() ) {
			return null;
		}
		return sdf.parse(approvedDateStr);
	}
	public void setApprovedDateStr(String approvedDateStr) {
		this.approvedDateStr = approvedDateStr;
	}
	public void setApprovedDate(Date approvedDate) {
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
	public String getAcknowledgedDateStr() {
		return acknowledgedDateStr;
	}
	public Date getAcknowledgedDate() throws ParseException {
		if( acknowledgedDateStr == null || acknowledgedDateStr.isEmpty() ) {
			return null;
		}
		return sdf.parse(acknowledgedDateStr);
	}
	public void setAcknowledgedDateStr(String acknowledgedDateStr) {
		this.acknowledgedDateStr = acknowledgedDateStr;
	}
	public void setAcknowledgedDate(Date acknowledgedDate) {
		if( acknowledgedDate != null ) {
			this.acknowledgedDateStr = sdf.format(acknowledgedDate);
		}
	}
	public Long getAcknowledgedBy() {
		return acknowledgedBy;
	}
	public void setAcknowledgedBy(Long acknowledgedBy) {
		this.acknowledgedBy = acknowledgedBy;
	}
	public String getAcknowledgedByName() {
		return acknowledgedByName;
	}

	public void setAcknowledgedByName(String acknowledgedByName) {
		this.acknowledgedByName = acknowledgedByName;
	}

	public String getLastUpdatedDateStr() {
		return lastUpdatedDateStr;
	}
	public Date getLastUpdatedDate() throws ParseException {
		if( lastUpdatedDateStr == null || lastUpdatedDateStr.isEmpty() ) {
			return null;
		}
		return sdf.parse(lastUpdatedDateStr);
	}
	public void setLastUpdatedDateStr(String lastUpdatedDateStr) {
		this.lastUpdatedDateStr = lastUpdatedDateStr;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		if( lastUpdatedDate != null ) {
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
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public List<Action> getAllowedStatusChanges() {
		return allowedStatusChanges;
	}
	public void setAllowedStatusChanges(List<Action> allowedStatusChanges) {
		this.allowedStatusChanges = allowedStatusChanges;
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
	public void addAllowedStatusChanges(Action action) {
		allowedStatusChanges.add(action);
	}
	public String getAllowedStatusChangesStr() {
		return allowedStatusChangesStr;
	}
	public void setAllowedStatusChangesStr(String allowedStatusChangesStr) {
		this.allowedStatusChangesStr = allowedStatusChangesStr;
	}
	public boolean isSubmitted() {
		return submitted;
	}
	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public String getPrRemark() {
		return prRemark;
	}
	public void setPrRemark(String prRemark) {
		this.prRemark = prRemark;
	}
}
