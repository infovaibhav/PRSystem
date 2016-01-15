/**
 * 
 */
package org.iry.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.iry.dto.pr.PurchaseRequestSearchCriteria;
import org.iry.dto.pr.PurchaseRequisitionDto;
import org.iry.service.pr.PurchaseRequisitionService;
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
	
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PurchaseRequisitionDto> savePurchaseRequisition(@RequestBody PurchaseRequisitionDto purchaseRequisitionDto) {
		try {
			purchaseRequisitionDto = prService.save(purchaseRequisitionDto);
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
			return new ResponseEntity<PurchaseRequisitionDto>(purchaseRequisitionDto, HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in fetching Purchase Requisitions...", e);
			return new ResponseEntity<PurchaseRequisitionDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}