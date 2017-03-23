/**
 * 
 */
package org.iry.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.iry.dto.Action;
import org.iry.dto.BaseDto;
import org.iry.dto.ResponseDto;
import org.iry.dto.pr.PurchaseRequestSearchCriteria;
import org.iry.dto.pr.PurchaseRequisitionDto;
import org.iry.dto.pr.PurchaseRequisitionItemsDto;
import org.iry.exceptions.InvalidRequestException;
import org.iry.model.pr.PurchaseRequisitionStatus;
import org.iry.model.user.User;
import org.iry.service.pr.PurchaseRequisitionService;
import org.iry.service.user.UserDetails;
import org.iry.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vaibhavp
 *
 */
@RestController
@RequestMapping("/rest/purchaseRequest")
public class PRRestController {
	
	private static final Logger log = Logger.getLogger(PRRestController.class);
	
	@Autowired
	PurchaseRequisitionService prService;
	
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> savePurchaseRequisition(@RequestBody PurchaseRequisitionDto purchaseRequisitionDto) {
		try {
			User user = SpringContextUtil.getUser();
			if( user == null ) {
				throw new InvalidRequestException("Logged in user information not found.");
			}
			prService.save(purchaseRequisitionDto, user.getId(), user.getFullName());
			
			Boolean notificationSent = null;

			if( purchaseRequisitionDto.isSubmitted() && user.isEmailNotification()) {
				notificationSent = prService.sendEmailNotification(purchaseRequisitionDto.getPrNo(), user);
			}
			String response = null;
			if( notificationSent == null ) {
				response = "{\"response\":"+"\"PR has been saved successfully. Email not Sent.\"}";
			} else if( notificationSent ) {
				response = "{\"response\":"+"\"PR has been saved successfully. Email Sent.\"}";
			} else {
				response = "{\"response\":"+"\"PR has been saved successfully. Error while sending email.\"}";
			}
			return new ResponseEntity<String>(response, HttpStatus.OK);
			
		} catch( InvalidRequestException e ) {
			log.error("Error in saving Purchase Requisition...", e);
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		} catch( Exception e ) {
			log.error("Error in saving Purchase Requisition...", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/_search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseDto> searchPurchaseRequisitions(@RequestBody PurchaseRequestSearchCriteria searchCriteria) {
		try {
			List<PurchaseRequisitionDto> purchaseRequisitionDtos = prService.findPurchaseRequisitions(searchCriteria);
			updateAllowedPrActions(purchaseRequisitionDtos, SpringContextUtil.getUserDetails());
			
			int recordCnt = purchaseRequisitionDtos.isEmpty() ? 0 : purchaseRequisitionDtos.get(0).getTotalRecords();
	        int pages = recordCnt % searchCriteria.getPageSize();
	        if (pages > 0) pages = (recordCnt / searchCriteria.getPageSize()) + 1;
	        else  pages = recordCnt / searchCriteria.getPageSize();
	        
	        ResponseDto response = new ResponseDto();
	        response.setPage( searchCriteria.getPage() );
	        response.setTotal(pages);
	        response.setRecords(recordCnt);
	        response.setRows((List)purchaseRequisitionDtos);
	        
			return new ResponseEntity<BaseDto>(response, HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in searching Purchase Requisitions...", e);
			return new ResponseEntity<BaseDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/{prNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PurchaseRequisitionDto> getPurchaseRequisition(@PathVariable("prNo") String prNo) {
		try {
			PurchaseRequisitionDto purchaseRequisitionDto = prService.findByPrNo(prNo);
			updateAllowedPrActions(purchaseRequisitionDto, SpringContextUtil.getUserDetails());
			return new ResponseEntity<PurchaseRequisitionDto>(purchaseRequisitionDto, HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in fetching Purchase Requisition...", e);
			return new ResponseEntity<PurchaseRequisitionDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/{prNo}/items", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PurchaseRequisitionItemsDto>> getPurchaseRequisitionItems(@PathVariable("prNo") String prNo) {
		try {
			PurchaseRequisitionDto purchaseRequisitionDto = prService.findByPrNo(prNo);
			updateAllowedPrActions(purchaseRequisitionDto, SpringContextUtil.getUserDetails());
			return new ResponseEntity<List<PurchaseRequisitionItemsDto>>(purchaseRequisitionDto.getPurchaseRequisionItems(), HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in fetching Purchase Requisition...", e);
			return new ResponseEntity<List<PurchaseRequisitionItemsDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/{prNo}/authorize", method = RequestMethod.PUT)
	public ResponseEntity<String> authorize(@PathVariable("prNo") String prNo) {
		try {
			User user = SpringContextUtil.getUser();
			if( user == null ) {
				throw new InvalidRequestException("Logged in user information not found.");
			}
			
			boolean notificationSent = updatePrStatusAndNotify(prNo, PurchaseRequisitionStatus.AUTHORIZED.getStatus(), user, null);
			
			return new ResponseEntity<String>("PR has been authorized. Email" + (notificationSent ? " " : " not ") + "Sent.", HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in Authorizing Purchase Requisition...", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/{prNo}/approve", method = RequestMethod.PUT)
	public ResponseEntity<String> approve(@PathVariable("prNo") String prNo) {
		try {
			User user = SpringContextUtil.getUser();
			if( user == null ) {
				throw new InvalidRequestException("Logged in user information not found.");
			}
			
			boolean notificationSent = updatePrStatusAndNotify(prNo, PurchaseRequisitionStatus.APPROVED.getStatus(), user, null);
			
			return new ResponseEntity<String>("PR has been authorized. Email" + (notificationSent ? " " : " not ") + "Sent.", HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in Approving Purchase Requisition...", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/{prNo}/acknowledge", method = RequestMethod.PUT)
	public ResponseEntity<String> acknowledge(@PathVariable("prNo") String prNo) {
		try {
			User user = SpringContextUtil.getUser();
			if( user == null ) {
				throw new InvalidRequestException("Logged in user information not found.");
			}
			
			boolean notificationSent = updatePrStatusAndNotify(prNo, PurchaseRequisitionStatus.ACKNOWLEDGED.getStatus(), user, null);
			
			return new ResponseEntity<String>("PR has been acknowledged. Email" + (notificationSent ? " " : " not ") + "Sent.", HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in Acknowledging Purchase Requisition...", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/{prNo}/updatestatus/{status}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateStatus(@PathVariable("prNo") String prNo, @PathVariable("status") String status, @RequestBody Map<String, String> body) {
		try {
			User user = SpringContextUtil.getUser();
			if( user == null ) {
				throw new InvalidRequestException("Logged in user information not found.");
			}
			
			boolean notificationSent = updatePrStatusAndNotify(prNo, status, user, body.get("remark") == "" ? null : body.get("remark"));
			String response = null;
			if( notificationSent ) {
				response = "{\"response\":"+"\"PR has been saved successfully. Email Sent.\"}";
			} else {
				response = "{\"response\":"+"\"PR has been saved successfully. Error while sending email.\"}";
			}
			return new ResponseEntity<String>(response, HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in Updating Status of Purchase Requisition...", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private boolean updatePrStatusAndNotify(String prNo, String status, User user, String remark) {
		log.info("Updating #PR- " + prNo + " with #Status- " + status + " #Remark - " + remark);
		prService.updatePrStatus(prNo, status, user.getId(), user.getFullName(), remark);
		return user.isEmailNotification() ? prService.sendEmailNotification(prNo, user) : false ;
	}
	
	private void updateAllowedPrActions(List<PurchaseRequisitionDto> dtos, UserDetails userDetails) {
		if( dtos != null ) {
			for (PurchaseRequisitionDto dto : dtos) {
				updateAllowedPrActions(dto, userDetails);
			}
		}
	}
	
	private void updateAllowedPrActions(PurchaseRequisitionDto dto, UserDetails userDetails) {
		String status = dto.getStatus();
		if( status.equals(PurchaseRequisitionStatus.INITIAL.getStatus()) ) {
			
			if( dto.getCreatedBy().longValue() == userDetails.getUser().getId().longValue() ) {
				dto.setEditable(true);
			}
			if( dto.getCreatedBy().longValue() == userDetails.getUser().getId().longValue() ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.SUBMITTED.getStatus(), "Submit"));
			}
			if( userDetails.getAllowedActions().cancelPr ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.CANCELLED.getStatus(), "Cancel"));
			}
			
		} else if( status.equals(PurchaseRequisitionStatus.SUBMITTED.getStatus()) ) {
			if( dto.getCreatedBy().longValue() == userDetails.getUser().getId().longValue() ) {
				dto.setEditable(true);
			}

			if( userDetails.getAllowedActions().authorizePr ) {
				dto.setEditable(true);
				dto.setEditablePrRemark(true);
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.AUTHORIZED.getStatus(), "Authorize"));
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.INITIAL.getStatus(), "Reopen"));
			}
			if( userDetails.getAllowedActions().cancelPr ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.CANCELLED.getStatus(), "Cancel"));
			}
			
		} else if( status.equals(PurchaseRequisitionStatus.AUTHORIZED.getStatus()) ) {
			
			if( userDetails.getAllowedActions().approvePr ) {
				dto.setEditable(true);
				dto.setEditablePrRemark(true);
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.APPROVED.getStatus(), "Approve"));
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.INITIAL.getStatus(), "Reopen"));
			}
			if( userDetails.getAllowedActions().cancelPr ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.CANCELLED.getStatus(), "Cancel"));
			}
			
		} else if( status.equals(PurchaseRequisitionStatus.APPROVED.getStatus()) ) {
			if( userDetails.getAllowedActions().poHold ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.POHOLD.getStatus(), "POHold"));
			}

			if( userDetails.getAllowedActions().acknowledgePr ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.ACKNOWLEDGED.getStatus(), "Acknowledge"));
			}
			if( userDetails.getAllowedActions().cancelPr
					&& userDetails.getAllowedActions().approvePr ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.CANCELLED.getStatus(), "Cancel"));
			}
		} else if ( status.equals(PurchaseRequisitionStatus.POHOLD.getStatus()) ) {
			if( userDetails.getAllowedActions().acknowledgePr ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.ACKNOWLEDGED.getStatus(), "Acknowledge"));
			}
			if( userDetails.getAllowedActions().cancelPr
					&& userDetails.getAllowedActions().approvePr ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.CANCELLED.getStatus(), "Cancel"));
			}
		} else if( status.equals(PurchaseRequisitionStatus.ACKNOWLEDGED.getStatus()) ) {
			
			if( userDetails.getAllowedActions().editPrItemsRemark ) {
				dto.setEditable(true);
				dto.setEditablePrItemsRemark(true);
			}
			if( userDetails.getAllowedActions().updateRequestQuote ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.REQUEST_FOR_QUOTE.getStatus()));
			}
			if( userDetails.getAllowedActions().updateReceiveQuote ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.QUOTE_RECEIVED.getStatus()));
			}
			if( userDetails.getAllowedActions().updateFinalizeQuote ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.QUOTE_FINALIZATION.getStatus()));
			}
			if( userDetails.getAllowedActions().updatePoCreated ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.PARTIAL_PO_CREATED.getStatus()));
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.PO_CREATED.getStatus()));
			}
			if( userDetails.getAllowedActions().cancelPr
					&& userDetails.getAllowedActions().approvePr) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.CANCELLED.getStatus(), "Cancel"));
			}
		} else if( status.equals(PurchaseRequisitionStatus.REQUEST_FOR_QUOTE.getStatus()) ) {
			
			if( userDetails.getAllowedActions().editPrItemsRemark ) {
				dto.setEditable(true);
				dto.setEditablePrItemsRemark(true);
			}
			if( userDetails.getAllowedActions().updateReceiveQuote ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.QUOTE_RECEIVED.getStatus()));
			}
			if( userDetails.getAllowedActions().updateFinalizeQuote ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.QUOTE_FINALIZATION.getStatus()));
			}
			if( userDetails.getAllowedActions().updatePoCreated ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.PARTIAL_PO_CREATED.getStatus()));
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.PO_CREATED.getStatus()));
			}
			if( userDetails.getAllowedActions().cancelPr
					&& userDetails.getAllowedActions().approvePr) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.CANCELLED.getStatus(), "Cancel"));
			}
			
		} else if( status.equals(PurchaseRequisitionStatus.QUOTE_RECEIVED.getStatus()) ) {

			if( userDetails.getAllowedActions().editPrItemsRemark ) {
				dto.setEditable(true);
				dto.setEditablePrItemsRemark(true);
			}
			if( userDetails.getAllowedActions().updateFinalizeQuote ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.QUOTE_FINALIZATION.getStatus()));
			}
			if( userDetails.getAllowedActions().updatePoCreated ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.PARTIAL_PO_CREATED.getStatus()));
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.PO_CREATED.getStatus()));
			}
			if( userDetails.getAllowedActions().cancelPr
					&& userDetails.getAllowedActions().approvePr) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.CANCELLED.getStatus(), "Cancel"));
			}
			
		} else if( status.equals(PurchaseRequisitionStatus.QUOTE_FINALIZATION.getStatus()) ) {

			if( userDetails.getAllowedActions().editPrItemsRemark ) {
				dto.setEditable(true);
				dto.setEditablePrItemsRemark(true);
			}
			if( userDetails.getAllowedActions().updatePoCreated ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.PARTIAL_PO_CREATED.getStatus()));
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.PO_CREATED.getStatus()));
			}
			if( userDetails.getAllowedActions().cancelPr
					&& userDetails.getAllowedActions().approvePr) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.CANCELLED.getStatus(), "Cancel"));
			}
			
		} else if( status.equals(PurchaseRequisitionStatus.PARTIAL_PO_CREATED.getStatus()) ) {

			if( userDetails.getAllowedActions().editPrItemsRemark ) {
				dto.setEditable(true);
				dto.setEditablePrItemsRemark(true);
			}
			if( userDetails.getAllowedActions().updatePoCreated ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.PO_CREATED.getStatus()));
			}
			
		} else if( status.equals(PurchaseRequisitionStatus.PO_CREATED.getStatus()) ) {
			if( userDetails.getAllowedActions().editableInvoiceAndDt ) {
				dto.setEditable(true);
				dto.setEditableInvoiceAndDt(true);
			}
			if( userDetails.getAllowedActions().editPrItemsRemark ) {
				dto.setEditable(true);
				dto.setEditablePrItemsRemark(true);
			}
			if( userDetails.getAllowedActions().closedPo ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.PO_CLOSED.getStatus(), "POclosed"));
			}
		} else if( status.equals(PurchaseRequisitionStatus.CANCELLED.getStatus()) ) {
			
		} else if ( status.equals(PurchaseRequisitionStatus.PO_CLOSED.getStatus()) ) {
			if( userDetails.getAllowedActions().reopenPr ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.PO_REOPEN.getStatus(), "Reopen"));
			}
		} else if ( status.equals(PurchaseRequisitionStatus.PO_REOPEN.getStatus()) ) {
			if( dto.getCreatedBy().longValue() == userDetails.getUser().getId().longValue() ) {
				dto.setEditable(true);
			}
			if( dto.getCreatedBy().longValue() == userDetails.getUser().getId().longValue() ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.SUBMITTED.getStatus(), "Submit"));
			}
			if( userDetails.getAllowedActions().cancelPr ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.CANCELLED.getStatus(), "Cancel"));
			}

		}
		dto.setAllowedStatusChangesStr(dto.getAllowedStatusChanges().toString());
	}
	
	@RequestMapping(value = "/{prNo}/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public void downloadPurchaseRequisition(@PathVariable("prNo") String prNo,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		PurchaseRequisitionDto purchaseRequisitionDto = prService.findByPrNo(prNo);
		
		String reportName = purchaseRequisitionDto.getPrNo() + ".pdf";
		
	    response.addHeader("Content-Disposition", "attachment; filename=" + reportName);
	    response.setContentType("text/pdf");
	    
		prService.generatePdfReportStream(purchaseRequisitionDto, response.getOutputStream());
	    
	}
}