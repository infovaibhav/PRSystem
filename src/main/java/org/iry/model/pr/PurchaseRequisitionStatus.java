/**
 * 
 */
package org.iry.model.pr;

/**
 * @author vaibhavp
 *
 */
public enum PurchaseRequisitionStatus {
	
	INITIAL ("Initial"),
	SUBMITTED_FOR_AUTHORIZATION ("SubmittedForAuthorization"),
	SUBMITTED_FOR_APPROVAL ("SubmittedForApproval"),
	APPROVED ("Approved"),
	ACKNOWLEDGED ("Acknowledged"),
	REQUEST_FOR_QUOTE ("RequestForQuote"),
	QUOTE_RECEIVED ("QuoteReceived"),
	QUOTE_FINALIZATION ("QuoteFinalization"),
	PO_CREATED ("POCreated"),
	PARTIAL_PO_CREATED ("PartialPoCreated"),
	CANCELLED ("Cancelled");

	String purchaseRequisitionStatus;
	
	private PurchaseRequisitionStatus(String purchaseRequisitionStatus){
		this.purchaseRequisitionStatus = purchaseRequisitionStatus;
	}
	
	public String getStatus(){
		return purchaseRequisitionStatus;
	}
}
