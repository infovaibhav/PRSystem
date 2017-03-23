/**
 * 
 */
package org.iry.model.pr;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author vaibhavp
 *
 */
@Entity
@Table(name="PURCHASE_REQUISITION")
public class PurchaseRequisition implements Serializable {
	
	private static final long serialVersionUID = -4918952675293553505L;

	@Id
	@GenericGenerator(name="seq_id", strategy="org.iry.model.pr.PRNoGenerator")
	@GeneratedValue(generator="seq_id")
	@Column(name = "PR_NO", unique = true, nullable = false)
	private String prNo;

	@Column(name="PROJECT_NAME")
	private String projectName;

	@Column(name="PROJECT_CODE")
	private String projectCode;

	@Column(name="REV")
	private String rev;

	@Column(name="STATUS", nullable=false)
	private String status;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="CREATED_BY")
	private Long createdBy;

	@Column(name="CREATED_BY_NAME")
	private String createdByName;

	@Column(name="ASSIGNED_DATE")
	private Timestamp assignedDate;

	@Column(name="ASSIGNED_TO")
	private Long assignedTo;

	@Column(name="ASSIGNED_TO_NAME")
	private String assignedToName;

	@Column(name="AUTHORIZED_DATE")
	private Timestamp authorizedDate;

	@Column(name="AUTHORIZED_BY")
	private Long authorizedBy;

	@Column(name="AUTHORIZED_BY_NAME")
	private String authorizedByName;

	@Column(name="APPROVED_DATE")
	private Timestamp approvedDate;

	@Column(name="APPROVED_BY")
	private Long approvedBy;

	@Column(name="APPROVED_BY_NAME")
	private String approvedByName;

	@Column(name="ACKNOWLEDGED_DATE")
	private Timestamp acknowledgedDate;

	@Column(name="ACKNOWLEDGED_BY")
	private Long acknowledgedBy;

	@Column(name="ACKNOWLEDGED_BY_NAME")
	private String acknowledgedByName;

	@Column(name="LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name="LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name="LAST_UPDATED_BY_NAME")
	private String lastUpdatedByName;
	
	@Column(name="REMARK")
	private String prRemark;
	
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseRequisition", fetch = FetchType.LAZY)
	private Set<PurchaseRequisitionItems> purchaseRequisionItems = new HashSet<PurchaseRequisitionItems>();
	
	private transient int totalRecords = 0;
	
	private transient String prNoPrefix;
	
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
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(Timestamp assignedDate) {
		this.assignedDate = assignedDate;
	}
	public Long getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(Long assignedTo) {
		this.assignedTo = assignedTo;
	}
	public Timestamp getAuthorizedDate() {
		return authorizedDate;
	}
	public void setAuthorizedDate(Timestamp authorizedDate) {
		this.authorizedDate = authorizedDate;
	}
	public Long getAuthorizedBy() {
		return authorizedBy;
	}
	public void setAuthorizedBy(Long authorizedBy) {
		this.authorizedBy = authorizedBy;
	}
	public Timestamp getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Timestamp approvedDate) {
		this.approvedDate = approvedDate;
	}
	public Long getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public String getAssignedToName() {
		return assignedToName;
	}
	public void setAssignedToName(String assignedToName) {
		this.assignedToName = assignedToName;
	}
	public String getAuthorizedByName() {
		return authorizedByName;
	}
	public void setAuthorizedByName(String authorizedByName) {
		this.authorizedByName = authorizedByName;
	}
	public String getApprovedByName() {
		return approvedByName;
	}
	public void setApprovedByName(String approvedByName) {
		this.approvedByName = approvedByName;
	}
	public Timestamp getAcknowledgedDate() {
		return acknowledgedDate;
	}
	public void setAcknowledgedDate(Timestamp acknowledgedDate) {
		this.acknowledgedDate = acknowledgedDate;
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
	public String getLastUpdatedByName() {
		return lastUpdatedByName;
	}
	public void setLastUpdatedByName(String lastUpdatedByName) {
		this.lastUpdatedByName = lastUpdatedByName;
	}
	public Set<PurchaseRequisitionItems> getPurchaseRequisionItems() {
		return purchaseRequisionItems;
	}
	public void setPurchaseRequisionItems(Set<PurchaseRequisitionItems> purchaseRequisionItems) {
		this.purchaseRequisionItems = purchaseRequisionItems;
	}
	public String getPrNoPrefix() {
		return prNoPrefix;
	}
	public void setPrNoPrefix(String prNoPrefix) {
		this.prNoPrefix = prNoPrefix;
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
