/**
 * 
 */
package org.iry.service.pr;

import java.util.List;

import org.iry.dto.pr.PurchaseRequestSearchCriteria;
import org.iry.dto.pr.PurchaseRequisitionDto;

/**
 * @author vaibhavp
 *
 */
public interface PurchaseRequisitionService {
	
	PurchaseRequisitionDto save(PurchaseRequisitionDto prDto) throws Exception;

	List<PurchaseRequisitionDto> findPurchaseRequisitions(PurchaseRequestSearchCriteria searchCriteria);

	PurchaseRequisitionDto findByPrNo(String prNo);

}
