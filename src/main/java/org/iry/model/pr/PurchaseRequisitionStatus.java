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
	SUBMITTED ("Submitted"),
	AUTHORIZED ("Authorized"),
	APPROVED ("Approved"),
	POHOLD ("POHold"),
	ACKNOWLEDGED ("Acknowledged"),
	REQUEST_FOR_QUOTE ("RequestForQuote"),
	QUOTE_RECEIVED ("QuoteReceived"),
	QUOTE_FINALIZATION ("QuoteFinalization"),
	PO_CREATED ("POCreated"),
	PARTIAL_PO_CREATED ("PartialPoCreated"),
	CANCELLED ("Cancelled"),
	PO_CLOSED ("POClosed"),
	PO_REOPEN ("Reopen");
	
	String purchaseRequisitionStatus;
	
	private PurchaseRequisitionStatus(String purchaseRequisitionStatus){
		this.purchaseRequisitionStatus = purchaseRequisitionStatus;
	}
	
	public String getStatus(){
		return purchaseRequisitionStatus;
	}
}
