/**
 * 
 */
package org.iry.service.pr;

import java.io.File;
import java.util.List;

import org.iry.dto.pr.PurchaseRequestSearchCriteria;
import org.iry.dto.pr.PurchaseRequisitionDto;

/**
 * @author vaibhavp
 *
 */
public interface PurchaseRequisitionService {
	
	PurchaseRequisitionDto save(PurchaseRequisitionDto prDto, Long userId, String userName) throws Exception;

	List<PurchaseRequisitionDto> findPurchaseRequisitions(PurchaseRequestSearchCriteria searchCriteria) throws Exception;

	PurchaseRequisitionDto findByPrNo(String prNo);

	void updatePrStatus(String prNo, String status, Long userId, String userName);
	
	File generatePurchaseRequisitionReport(PurchaseRequisitionDto prDto) throws Exception;
}
