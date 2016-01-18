package org.iry.dao.pr;

import java.util.List;

import org.iry.dto.pr.PurchaseRequestSearchCriteria;
import org.iry.model.pr.PurchaseRequisition;

public interface PurchaseRequisitionDao {

	void save(PurchaseRequisition pr);
	
	PurchaseRequisition findById(String prNo);
	
	List<PurchaseRequisition> findPurchaseRequests(PurchaseRequestSearchCriteria searchCriteria);

}