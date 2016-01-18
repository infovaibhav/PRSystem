/**
 * 
 */
package org.iry.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.iry.dto.Action;
import org.iry.dto.pr.PurchaseRequestSearchCriteria;
import org.iry.dto.pr.PurchaseRequisitionDto;
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
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PurchaseRequisitionDto> savePurchaseRequisition(@RequestBody PurchaseRequisitionDto purchaseRequisitionDto) {
		try {
			User user = SpringContextUtil.getUser();
			if( user == null ) {
				throw new InvalidRequestException("Logged in user information not found.");
			}
			purchaseRequisitionDto = prService.save(purchaseRequisitionDto, user.getId(), user.getFullName());
			updateAllowedPrActions(purchaseRequisitionDto, SpringContextUtil.getUserDetails());
			return new ResponseEntity<PurchaseRequisitionDto>(purchaseRequisitionDto, HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in saving Purchase Requisition...", e);
			return new ResponseEntity<PurchaseRequisitionDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/_search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PurchaseRequisitionDto>> searchPurchaseRequisitions(@RequestBody PurchaseRequestSearchCriteria searchCriteria) {
		try {
			List<PurchaseRequisitionDto> purchaseRequisitionDtos = prService.findPurchaseRequisitions(searchCriteria);
			updateAllowedPrActions(purchaseRequisitionDtos, SpringContextUtil.getUserDetails());
			return new ResponseEntity<List<PurchaseRequisitionDto>>(purchaseRequisitionDtos, HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in searching Purchase Requisitions...", e);
			return new ResponseEntity<List<PurchaseRequisitionDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
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
	
	@RequestMapping(value = "/{prNo}/authorize", method = RequestMethod.PUT)
	public ResponseEntity<String> authorize(@PathVariable("prNo") String prNo) {
		try {
			User user = SpringContextUtil.getUser();
			if( user == null ) {
				throw new InvalidRequestException("Logged in user information not found.");
			}
			prService.updatePrStatus(prNo, PurchaseRequisitionStatus.AUTHORIZED.getStatus(), user.getId(), user.getFullName());
			return new ResponseEntity<String>("PR has been authorized...", HttpStatus.OK);
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
			prService.updatePrStatus(prNo, PurchaseRequisitionStatus.APPROVED.getStatus(), user.getId(), user.getFullName());
			return new ResponseEntity<String>("PR has been authorized...", HttpStatus.OK);
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
			prService.updatePrStatus(prNo, PurchaseRequisitionStatus.ACKNOWLEDGED.getStatus(), user.getId(), user.getFullName());
			return new ResponseEntity<String>("PR has been acknowledged...", HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in Acknowledging Purchase Requisition...", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/{prNo}/updatestatus", method = RequestMethod.PUT)
	public ResponseEntity<String> updateStauts(@PathVariable("prNo") String prNo, @RequestParam String status) {
		try {
			User user = SpringContextUtil.getUser();
			if( user == null ) {
				throw new InvalidRequestException("Logged in user information not found.");
			}
			prService.updatePrStatus(prNo, status, user.getId(), user.getFullName());
			return new ResponseEntity<String>("PR has been authorized...", HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in Updating Status of Purchase Requisition...", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
		if( status.equals(PurchaseRequisitionStatus.INITIAL) ) {
			
			if( dto.getCreatedBy().longValue() == userDetails.getUser().getId().longValue() 
					|| userDetails.getAllowedActions().authorizePr
					|| userDetails.getAllowedActions().approvePr ) {
				dto.setEditable(true);
			}
			if( userDetails.getAllowedActions().cancelPr ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.CANCELLED.getStatus(), "Cancel"));
			}
			
		} else if( status.equals(PurchaseRequisitionStatus.SUBMITTED.getStatus()) ) {
			
			if( dto.getCreatedBy().longValue() == userDetails.getUser().getId().longValue() 
					|| userDetails.getAllowedActions().authorizePr
					|| userDetails.getAllowedActions().approvePr ) {
				dto.setEditable(true);
			}
			if( userDetails.getAllowedActions().cancelPr ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.CANCELLED.getStatus(), "Cancel"));
			}
			if( userDetails.getAllowedActions().authorizePr ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.AUTHORIZED.getStatus(), "Authorize"));
			}
			if( userDetails.getAllowedActions().approvePr ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.APPROVED.getStatus(), "Approve"));
			}
			
		} else if( status.equals(PurchaseRequisitionStatus.AUTHORIZED.getStatus()) ) {
			
			if( userDetails.getAllowedActions().cancelPr ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.CANCELLED.getStatus(), "Cancel"));
			}
			if( userDetails.getAllowedActions().approvePr ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.APPROVED.getStatus(), "Approve"));
			}
			
		} else if( status.equals(PurchaseRequisitionStatus.APPROVED.getStatus()) ) {
			
			if( userDetails.getAllowedActions().acknowledgePr ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.ACKNOWLEDGED.getStatus(), "Acknowledge"));
			}
			
		} else if( status.equals(PurchaseRequisitionStatus.ACKNOWLEDGED.getStatus()) ) {
			
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

			if( userDetails.getAllowedActions().updatePoCreated ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.PARTIAL_PO_CREATED.getStatus()));
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.PO_CREATED.getStatus()));
			}
			if( userDetails.getAllowedActions().cancelPr
					&& userDetails.getAllowedActions().approvePr) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.CANCELLED.getStatus(), "Cancel"));
			}
			
		} else if( status.equals(PurchaseRequisitionStatus.PARTIAL_PO_CREATED.getStatus()) ) {

			if( userDetails.getAllowedActions().updatePoCreated ) {
				dto.addAllowedStatusChanges(new Action(PurchaseRequisitionStatus.PO_CREATED.getStatus()));
			}
			
		} else if( status.equals(PurchaseRequisitionStatus.PO_CREATED.getStatus()) ) {
			
		} else if( status.equals(PurchaseRequisitionStatus.CANCELLED.getStatus()) ) {
			
		}
	}
	
}