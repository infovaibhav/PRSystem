/**
 * 
 */
package org.iry.controller;

import java.util.ArrayList;
import java.util.List;

import org.iry.dto.pr.PurchaseRequestSearchCriteria;
import org.iry.dto.pr.PurchaseRequisitionDto;
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
	
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PurchaseRequisitionDto> createPurchaseRequisition(@RequestBody PurchaseRequisitionDto purchaseRequisitionDto) {
		
		
		return new ResponseEntity<PurchaseRequisitionDto>(purchaseRequisitionDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/_search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PurchaseRequisitionDto>> searchPurchaseRequisitions(@RequestBody PurchaseRequestSearchCriteria searchCriteria) {
		List<PurchaseRequisitionDto> purchaseRequisitionDtos = new ArrayList<PurchaseRequisitionDto>();
		
		return new ResponseEntity<List<PurchaseRequisitionDto>>(purchaseRequisitionDtos, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{prNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PurchaseRequisitionDto> getPurchaseRequisition(@PathVariable("prNo") String prNo) {
		
		return new ResponseEntity<PurchaseRequisitionDto>(new PurchaseRequisitionDto(), HttpStatus.OK);
	}
}
