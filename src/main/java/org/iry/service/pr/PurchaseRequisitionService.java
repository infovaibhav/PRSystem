/**
 * 
 */
package org.iry.service.pr;

import java.io.OutputStream;
import java.util.List;

import org.iry.dto.pr.PurchaseRequestSearchCriteria;
import org.iry.dto.pr.PurchaseRequisitionDto;
import org.iry.model.user.User;

/**
 * @author vaibhavp
 *
 */
public interface PurchaseRequisitionService {
	
	PurchaseRequisitionDto save(PurchaseRequisitionDto prDto, Long userId, String userName) throws Exception;

	List<PurchaseRequisitionDto> findPurchaseRequisitions(PurchaseRequestSearchCriteria searchCriteria) throws Exception;

	PurchaseRequisitionDto findByPrNo(String prNo);

	void updatePrStatus(String prNo, String status, Long userId, String userName, String remark);
	
	void generatePdfReportStream(PurchaseRequisitionDto prDto, OutputStream pdfStream) throws Exception;

	boolean sendEmailNotification(String prNo, User user);
}
