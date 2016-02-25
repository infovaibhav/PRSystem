/**
 * 
 */
package org.iry.service.pr;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.iry.dao.pr.PurchaseRequisitionDao;
import org.iry.dao.user.UserDao;
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
	
	@Autowired
	private UserDao userDao;

	@Override
	public PurchaseRequisitionDto save(PurchaseRequisitionDto prDto, Long userId, String userName) throws Exception {
		PurchaseRequisition pr = convertToPojo(prDto, userId, userName);
		prDao.save(pr);
		return convertToDto(pr, true);
	}
	
	@Override
	public List<PurchaseRequisitionDto> findPurchaseRequisitions(PurchaseRequestSearchCriteria searchCriteria) throws Exception {
		List<PurchaseRequisition> prs = prDao.findPurchaseRequests(searchCriteria);
		return convertToDto(prs, false);
	}
	
	@Override
	public PurchaseRequisitionDto findByPrNo(String prNo) {
		PurchaseRequisition pr = prDao.findById(prNo);
		return convertToDto(pr, true);
	}
	
	@Override
	public void updatePrStatus(String prNo, String status, Long userId, String userName) {
		PurchaseRequisition pr = prDao.findById(prNo);
		if( pr == null ) {
			throw new InvalidRequestException("Purchase requisition does not exists.");
		}
		
		setStatusInformation(pr, status, userId, userName);
		
		prDao.save(pr);
	}
	
	private void setStatusInformation(PurchaseRequisition pr, String status, Long userId, String userName) {
		if( userName == null ) {
			userName = userDao.getFullNameById(userId);
		}
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		if( status != null && status.trim().length() != 0 ) {
			pr.setStatus(status);
			if( status.equals(PurchaseRequisitionStatus.INITIAL.getStatus()) ) {
				pr.setCreatedBy(userId);
				pr.setCreatedByName(userName);
				pr.setCreatedDate(currentTimestamp);
			} else if( status.equals(PurchaseRequisitionStatus.SUBMITTED.getStatus()) ) {
				pr.setCreatedBy(userId);
				pr.setCreatedByName(userName);
				pr.setCreatedDate(currentTimestamp);
			} else if( status.equals(PurchaseRequisitionStatus.AUTHORIZED.getStatus()) ) {
				pr.setAuthorizedBy(userId);
				pr.setAuthorizedByName(userName);
				pr.setAuthorizedDate(currentTimestamp);
			} else if( status.equals(PurchaseRequisitionStatus.APPROVED.getStatus()) ) {
				pr.setApprovedBy(userId);
				pr.setApprovedByName(userName);
				pr.setApprovedDate(currentTimestamp);
				if( pr.getAuthorizedBy() == null ) {
					pr.setAuthorizedBy(userId);
					pr.setAuthorizedByName(userName);
					pr.setAuthorizedDate(currentTimestamp);
				}
			} else if( status.equals(PurchaseRequisitionStatus.ACKNOWLEDGED.getStatus()) ) {
				pr.setAcknowledgedBy(userId);
				pr.setAcknowledgedByName(userName);
				pr.setAcknowledgedDate(currentTimestamp);
			}
		}
		pr.setLastUpdatedBy(userId);
		pr.setLastUpdatedByName(userName);
		pr.setLastUpdatedDate(currentTimestamp);
	}
	
	private PurchaseRequisition convertToPojo(PurchaseRequisitionDto dto, Long userId, String userName) throws Exception {
		PurchaseRequisition pr = null;
		if( dto.getPurchaseRequisionItems() == null || dto.getPurchaseRequisionItems().isEmpty() ) {
			throw new InvalidRequestException("Purchase Requisition Items not specified.");
		}
		if( dto.getPrNo() != null && !dto.getPrNo().isEmpty() ) {
			pr = prDao.findById(dto.getPrNo());
			if( pr == null ) {
				throw new InvalidRequestException("Requested PR does not exists.");
			}
			if(dto.isSubmitted()){
				setStatusInformation(pr, PurchaseRequisitionStatus.SUBMITTED.getStatus(), userId, userName);
			} else {
				setStatusInformation(pr, dto.getStatus(), userId, userName);
			}
		} else {
			pr = new PurchaseRequisition();
			pr.setPrNoPrefix(dto.getPrNoPrefix());
			setStatusInformation(pr, PurchaseRequisitionStatus.INITIAL.getStatus(), userId, userName);
		}
		pr.setProjectCode(dto.getProjectCode());
		pr.setProjectName(dto.getProjectName());
		pr.setRev(dto.getRev());
		
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
		
		if( dto.getPriId() == null ) {
			prItem = new PurchaseRequisitionItems();
		} else {
			Set<PurchaseRequisitionItems> existingPrItems = existingPr.getPurchaseRequisionItems();
			if( existingPrItems != null && !existingPrItems.isEmpty() ) {
				for (PurchaseRequisitionItems existingPrItem : existingPrItems) {
					if( dto.getPriId().longValue() == existingPrItem.getId().longValue() ) {
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
		if( dto.getRequiredByDate() != null ) {
			prItem.setRequiredByDate(new Timestamp(dto.getRequiredByDate().getTime()));
		}
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
		dto.setCreatedDate(pr.getCreatedDate());
		dto.setCreatedBy(pr.getCreatedBy());
		dto.setCreatedByName(pr.getCreatedByName());
		dto.setAssignedDate(pr.getAssignedDate());
		dto.setAssignedTo(pr.getAssignedTo());
		dto.setAssignedToName(pr.getAssignedToName());
		dto.setAuthorizedDate(pr.getAuthorizedDate());
		dto.setAuthorizedBy(pr.getAuthorizedBy());
		dto.setAuthorizedByName(pr.getAuthorizedByName());
		dto.setApprovedDate(pr.getApprovedDate());
		dto.setApprovedBy(pr.getApprovedBy());
		dto.setApprovedByName(pr.getApprovedByName());
		dto.setAcknowledgedDate(pr.getAcknowledgedDate());
		dto.setAcknowledgedBy(pr.getAcknowledgedBy());
		dto.setAcknowledgedByName(pr.getAcknowledgedByName());
		dto.setLastUpdatedDate(pr.getLastUpdatedDate());
		dto.setLastUpdatedBy(pr.getLastUpdatedBy());
		dto.setLastUpdatedByName(pr.getApprovedByName());
		if( loadPrItems ) {
			for (PurchaseRequisitionItems prItem : pr.getPurchaseRequisionItems()) {
				dto.addPurchaseRequisionItems(convertToDto(prItem));
			}
		}
		return dto;
	}
	
	private PurchaseRequisitionItemsDto convertToDto(PurchaseRequisitionItems prItem) {
		PurchaseRequisitionItemsDto dto = new PurchaseRequisitionItemsDto();
		dto.setPriId(prItem.getId());
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
		dto.setRequiredByDate(prItem.getRequiredByDate());
		return dto;
	}

	@Override
	public File generatePurchaseRequisitionReport(PurchaseRequisitionDto prDto) throws Exception {
		String filePath = System.getProperty("user.home") + File.separator + "iryReports";
		File folder = new File(filePath + File.separator + SpringContextUtil.getUserDetails().getUsername());
		if (!folder.exists()) {
            folder.mkdirs();
        }
		Date today = new Date();
		String reportFileName = "" + SpringContextUtil.getUserDetails().getUsername() + "_" + prDto.getPrNo()+ "_" + "_" + new java.sql.Timestamp(today.getTime()).toString();
        reportFileName = reportFileName.replaceAll("[^a-zA-Z0-9_]", "");
        String reportFile = folder.getAbsolutePath() + File.separatorChar + reportFileName;
        reportFile = reportFile + ".pdf";
        File reportFilePdf = new File(reportFile);
        Map<String, Object> reportParams = new HashMap<String, Object>();
		reportParams.put("prNo", prDto.getPrNo());
		reportParams.put("projectName", prDto.getProjectName());
		reportParams.put("projectCode", prDto.getProjectCode());
		reportParams.put("rev", prDto.getRev());
		JRDataSource reportDataSource = new JRBeanCollectionDataSource(prDto.getPurchaseRequisionItems());
		exportPDFReport(reportFilePdf, reportParams, "IRYReport1.jasper", reportDataSource);
		return reportFilePdf;
	}
	
	private void exportPDFReport(File reportFile, Map<String, Object> reportParams, String jasperFile, JRDataSource dataSource) throws Exception{
		InputStream jasperFileStream = null;
		try {
			jasperFileStream = this.getClass().getClassLoader().getResourceAsStream(jasperFile);
			JasperPrint jrPrint = JasperFillManager.fillReport(jasperFileStream, reportParams, dataSource);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jrPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE, reportFile);
			exporter.exportReport();
		} finally {
			if (jasperFileStream != null) {
				try {
					jasperFileStream.close();
				} catch (IOException ignored) {
				}
			}
		}
	}
}
