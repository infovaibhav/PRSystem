/**
 * 
 */
package org.iry.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.iry.dto.pr.PurchaseRequestSearchCriteria;
import org.iry.dto.pr.PurchaseRequisitionDto;
import org.iry.exceptions.InvalidRequestException;
import org.iry.model.pr.PurchaseRequisitionStatus;
import org.iry.service.pr.PurchaseRequisitionService;
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
	
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PurchaseRequisitionDto> savePurchaseRequisition(@RequestBody PurchaseRequisitionDto purchaseRequisitionDto) {
		try {
			Long userId = SpringContextUtil.getUserId();
			if( userId == null ) {
				throw new InvalidRequestException("Logged in user information not found.");
			}
			purchaseRequisitionDto = prService.save(purchaseRequisitionDto, userId);
			updateAllowedPrActions(purchaseRequisitionDto);
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
			updateAllowedPrActions(purchaseRequisitionDtos);
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
			updateAllowedPrActions(purchaseRequisitionDto);
			return new ResponseEntity<PurchaseRequisitionDto>(purchaseRequisitionDto, HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in fetching Purchase Requisition...", e);
			return new ResponseEntity<PurchaseRequisitionDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/{prNo}/authorize", method = RequestMethod.PUT)
	public ResponseEntity<String> authorize(@PathVariable("prNo") String prNo) {
		try {
			Long userId = SpringContextUtil.getUserId();
			if( userId == null ) {
				throw new InvalidRequestException("Logged in user information not found.");
			}
			prService.updatePrStatus(prNo, PurchaseRequisitionStatus.AUTHORIZED.getStatus(), userId);
			return new ResponseEntity<String>("PR has been authorized...", HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in Authorizing Purchase Requisition...", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/{prNo}/approve", method = RequestMethod.PUT)
	public ResponseEntity<String> approve(@PathVariable("prNo") String prNo) {
		try {
			Long userId = SpringContextUtil.getUserId();
			if( userId == null ) {
				throw new InvalidRequestException("Logged in user information not found.");
			}
			prService.updatePrStatus(prNo, PurchaseRequisitionStatus.APPROVED.getStatus(), userId);
			return new ResponseEntity<String>("PR has been authorized...", HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in Approving Purchase Requisition...", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/{prNo}/updatestatus", method = RequestMethod.PUT)
	public ResponseEntity<String> updateStauts(@PathVariable("prNo") String prNo, @RequestParam String status) {
		try {
			Long userId = SpringContextUtil.getUserId();
			if( userId == null ) {
				throw new InvalidRequestException("Logged in user information not found.");
			}
			prService.updatePrStatus(prNo, status, userId);
			return new ResponseEntity<String>("PR has been authorized...", HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in Updating Status of Purchase Requisition...", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private void updateAllowedPrActions(List<PurchaseRequisitionDto> dtos) {
		if( dtos != null ) {
			for (PurchaseRequisitionDto dto : dtos) {
				updateAllowedPrActions(dto);
			}
		}
	}
	
	private void updateAllowedPrActions(PurchaseRequisitionDto dto) {
		
	}
	
}