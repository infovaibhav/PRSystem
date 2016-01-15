/**
 * 
 */
package org.iry.service.pr;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.iry.dao.pr.PurchaseRequisitionDao;
import org.iry.dto.pr.PurchaseRequestSearchCriteria;
import org.iry.dto.pr.PurchaseRequisitionDto;
import org.iry.dto.pr.PurchaseRequisitionItemsDto;
import org.iry.exceptions.InvalidRequestException;
import org.iry.model.pr.PurchaseRequisition;
import org.iry.model.pr.PurchaseRequisitionItems;
import org.iry.model.pr.PurchaseRequisitionStatus;
import org.iry.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author vaibhavp
 *
 */
@Service("purchaseRequisitionService")
@Transactional
public class PurchaseRequisitionServiceImpl implements PurchaseRequisitionService {
	
	@Autowired
	private PurchaseRequisitionDao prDao;

	@Override
	public PurchaseRequisitionDto save(PurchaseRequisitionDto prDto) throws Exception {
		PurchaseRequisition pr = convertToPojo(prDto);
		prDao.save(pr);
		return convertToDto(pr, true);
	}
	
	@Override
	public List<PurchaseRequisitionDto> findPurchaseRequisitions(PurchaseRequestSearchCriteria searchCriteria) {
		List<PurchaseRequisition> prs = prDao.findPurchaseRequests(searchCriteria);
		return convertToDto(prs, false);
	}
	
	@Override
	public PurchaseRequisitionDto findByPrNo(String prNo) {
		PurchaseRequisition pr = prDao.findById(prNo);
		return convertToDto(pr, false);
	}
	
	private PurchaseRequisition convertToPojo(PurchaseRequisitionDto dto) throws Exception {
		PurchaseRequisition pr = null;
		if( dto.getPurchaseRequisionItems() == null || dto.getPurchaseRequisionItems().isEmpty() ) {
			throw new InvalidRequestException("Purchase Requisition Items not specified.");
		}
		Long userId = SpringContextUtil.getUserId();
		if( userId == null ) {
			throw new InvalidRequestException("Logged in user information not found.");
		}
		if( dto.getPrNo() != null && !dto.getPrNo().isEmpty() ) {
			pr = prDao.findById(dto.getPrNo());
			if( pr == null ) {
				throw new InvalidRequestException("Requested PR does not exists.");
			}
			pr.setStatus(dto.getStatus());
		} else {
			pr = new PurchaseRequisition();
			pr.setStatus(PurchaseRequisitionStatus.INITIAL.getStatus());
			pr.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			pr.setCreatedBy(userId);
		}
		pr.setProjectCode(dto.getProjectCode());
		pr.setProjectName(dto.getProjectName());
		pr.setRev(dto.getRev());
		pr.setLastUpdatedDate(new Timestamp(System.currentTimeMillis()));
		pr.setLastUpdatedBy(userId);
		
		Set<PurchaseRequisitionItems> newPrItems = new HashSet<PurchaseRequisitionItems>();
		for (PurchaseRequisitionItemsDto prItemsDto : dto.getPurchaseRequisionItems()) {
			newPrItems.add( convertToPojo(prItemsDto, pr) );
		}
		
		pr.setPurchaseRequisionItems(newPrItems);
		
		return pr;
	}
	
	private PurchaseRequisitionItems convertToPojo(PurchaseRequisitionItemsDto dto, PurchaseRequisition existingPr) throws Exception {
		PurchaseRequisitionItems prItem = null;
		if( dto.getTotalQuantityRequired() == 0 ) {
			throw new InvalidRequestException("Required Quantity zero.");
		}
		if( dto.getTotalQuantityRequired() <= dto.getQuantityInStock() ) {
			throw new InvalidRequestException("Required Quantity is less than or equal to quantity in stock.");
		}
		
		if( dto.getId() == null ) {
			prItem = new PurchaseRequisitionItems();
		} else {
			Set<PurchaseRequisitionItems> existingPrItems = existingPr.getPurchaseRequisionItems();
			if( existingPrItems != null && !existingPrItems.isEmpty() ) {
				for (PurchaseRequisitionItems existingPrItem : existingPrItems) {
					if( dto.getId().longValue() == existingPrItem.getId().longValue() ) {
						prItem = existingPrItem;
						break;
					}
				}
			}
			if( prItem == null ) {
				throw new InvalidRequestException("Requested PR Item does not exists.");
			}
		}
		
		prItem.setDescription(dto.getDescription());
		prItem.setTotalQuantityRequired(dto.getTotalQuantityRequired());
		prItem.setQuantityInStock(dto.getQuantityInStock());
		prItem.setQuantityTobePurchased(dto.getTotalQuantityRequired() - dto.getQuantityInStock());
		prItem.setUom(dto.getUom());
		prItem.setUnitCost(dto.getUnitCost());
		prItem.setApproxTotalCost(dto.getApproxTotalCost());
		prItem.setMake(dto.getMake());
		prItem.setCatNo(dto.getCatNo());
		prItem.setPreferredSupplier(dto.getPreferredSupplier());
		prItem.setRequiredByDate(new Timestamp(dto.getRequiredByDate().getTime()));
		prItem.setPurchaseRequisition(existingPr);
		
		return prItem;
	}
	
	private List<PurchaseRequisitionDto> convertToDto(List<PurchaseRequisition> prs, boolean loadPrItems) {
		List<PurchaseRequisitionDto> dtos = new ArrayList<PurchaseRequisitionDto>(prs.size());
		for (PurchaseRequisition pr : prs) {
			dtos.add(convertToDto(pr, loadPrItems));
		}
		return dtos;
	}
	
	private PurchaseRequisitionDto convertToDto(PurchaseRequisition pr, boolean loadPrItems) {
		PurchaseRequisitionDto dto = new PurchaseRequisitionDto();
		dto.setPrNo(pr.getPrNo());
		dto.setProjectCode(pr.getProjectCode());
		dto.setProjectName(pr.getProjectName());
		dto.setRev(pr.getRev());
		dto.setStatus(pr.getStatus());
		dto.setCreatedDateStr(pr.getCreatedDate());
		dto.setCreatedBy(pr.getCreatedBy());
		dto.setCreatedByName(pr.getCreatedBy() == null ? "" : pr.getCreatedBy().toString());
		dto.setAuthorizedDateStr(pr.getAuthorizedDate());
		dto.setAuthorizedBy(pr.getAuthorizedBy());
		dto.setAuthorizedByName(pr.getAuthorizedBy() == null ? "" : pr.getAuthorizedBy().toString());
		dto.setApprovedDateStr(pr.getApprovedDate());
		dto.setApprovedBy(pr.getApprovedBy());
		dto.setApprovedByName(pr.getApprovedBy() == null ? "" : pr.getApprovedBy().toString());
		if( loadPrItems ) {
			for (PurchaseRequisitionItems prItem : pr.getPurchaseRequisionItems()) {
				dto.addPurchaseRequisionItems(convertToDto(prItem));
			}
		}
		return dto;
	}
	
	private PurchaseRequisitionItemsDto convertToDto(PurchaseRequisitionItems prItem) {
		PurchaseRequisitionItemsDto dto = new PurchaseRequisitionItemsDto();
		dto.setId(prItem.getId());
		dto.setDescription(prItem.getDescription());
		dto.setTotalQuantityRequired(prItem.getTotalQuantityRequired());
		dto.setQuantityInStock(prItem.getQuantityInStock());
		dto.setQuantityTobePurchased(prItem.getTotalQuantityRequired() - prItem.getQuantityInStock());
		dto.setUom(prItem.getUom());
		dto.setUnitCost(prItem.getUnitCost());
		dto.setApproxTotalCost(prItem.getApproxTotalCost());
		dto.setMake(prItem.getMake());
		dto.setCatNo(prItem.getCatNo());
		dto.setPreferredSupplier(prItem.getPreferredSupplier());
		dto.setRequiredByDateStr(prItem.getRequiredByDate());
		return dto;
	}
	
}
