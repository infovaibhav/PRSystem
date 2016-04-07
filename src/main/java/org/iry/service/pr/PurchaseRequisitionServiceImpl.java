/**
 * 
 */
package org.iry.service.pr;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.log4j.Logger;
import org.iry.dao.pr.PurchaseRequisitionDao;
import org.iry.dao.user.UserDao;
import org.iry.dto.pr.PurchaseRequestSearchCriteria;
import org.iry.dto.pr.PurchaseRequisitionDto;
import org.iry.dto.pr.PurchaseRequisitionItemsDto;
import org.iry.exceptions.InvalidRequestException;
import org.iry.model.pr.PurchaseRequisition;
import org.iry.model.pr.PurchaseRequisitionItems;
import org.iry.model.pr.PurchaseRequisitionStatus;
import org.iry.model.user.User;
import org.iry.model.user.UserProfileType;
import org.iry.utils.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

/**
 * @author vaibhavp
 *
 */
@Service("purchaseRequisitionService")
@Transactional
public class PurchaseRequisitionServiceImpl implements PurchaseRequisitionService {
	
	private static final Logger log = Logger.getLogger(PurchaseRequisitionServiceImpl.class);
	
	@Autowired
	private PurchaseRequisitionDao prDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MailSender mailSender;

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
	public void updatePrStatus(String prNo, String status, Long userId, String userName, String remark) {
		StopWatch watch = new StopWatch();
		watch.start();
		PurchaseRequisition pr = prDao.findById(prNo);
		if( pr == null ) {
			throw new InvalidRequestException("Purchase requisition does not exists.");
		}
		if( remark != null && remark.trim().length() > 0 ) {
			pr.setPrRemark(remark);
		}
		this.setStatusInformation(pr, status, userId, userName);
		
		prDao.save(pr);
		log.debug("Updated #PR- " + prNo + " with #Stauts- " + status + " in #Millis-" + watch.getTotalTimeMillis());
	}
	
	private void setStatusInformation(PurchaseRequisition pr, String status, Long userId, String userName) {
		if( userName == null ) {
			userName = userDao.getFullNameById(userId);
		}
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		if( status != null && status.trim().length() != 0 ) {
			pr.setStatus(status);
			if( status.equals(PurchaseRequisitionStatus.INITIAL.getStatus()) ) {
				if( pr.getCreatedBy() == null ) {
					pr.setCreatedBy(userId);
					pr.setCreatedByName(userName);
					pr.setCreatedDate(currentTimestamp);
				}
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
		if( dto.getCode() == null || dto.getDescription() == null || dto.getQuantityRequired() == 0 || dto.getRequiredByDate() == null ) {
			throw new InvalidRequestException("Invalid Purchase Item record.");
		}
		PurchaseRequisitionItems prItem = null;
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

		prItem.setCode(dto.getCode());
		prItem.setDescription(dto.getDescription());
		prItem.setQuantityRequired(dto.getQuantityRequired());
		
		if( dto.getDiamensions() != null && dto.getDiamensions().trim().length() > 0 ) {
			prItem.setDiamensions(dto.getDiamensions());
		}
		if( dto.getUom() != null && dto.getUom().trim().length() > 0 ) {
			prItem.setUom(dto.getUom());
		}
		if( dto.getMake() != null && dto.getMake().trim().length() > 0 ) {
			prItem.setMake(dto.getMake());
		}
		if( dto.getSpecifications() != null && dto.getSpecifications().trim().length() > 0 ) {
			prItem.setSpecifications(dto.getSpecifications());
		}
		if( dto.getRequiredByDate() != null ) {
			prItem.setRequiredByDate(new Timestamp(dto.getRequiredByDate().getTime()));
		}
		if( dto.getDeliveryDate() != null ) {
			prItem.setDeliveryDate(new Timestamp(dto.getDeliveryDate().getTime()));
		}
		if( dto.getDeviation() != null && dto.getDeviation().trim().length() > 0 ) {
			prItem.setDeviation(dto.getDeviation());
		}
		prItem.setOrderedQuantity(dto.getOrderedQuantity());
		if( dto.getRemark() != null && dto.getRemark().trim().length() > 0 ) {
			prItem.setRemark(dto.getRemark());
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
		dto.setLastUpdatedDate(pr.getLastUpdatedDate());
		dto.setPrRemark(pr.getPrRemark());
		if( !pr.getStatus().equals(PurchaseRequisitionStatus.INITIAL.getStatus()) ) {
			dto.setSubmitted(true);
		}
		dto.setTotalRecords(pr.getTotalRecords());
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
		dto.setCode(prItem.getCode());
		dto.setDescription(prItem.getDescription());
		dto.setDiamensions(prItem.getDiamensions());
		dto.setQuantityRequired(prItem.getQuantityRequired());
		dto.setUom(prItem.getUom());
		dto.setMake(prItem.getMake());
		dto.setSpecifications(prItem.getSpecifications());
		dto.setRequiredByDate(prItem.getRequiredByDate());
		dto.setDeliveryDate(prItem.getDeliveryDate());
		dto.setDeviation(prItem.getDeviation());
		dto.setOrderedQuantity(prItem.getOrderedQuantity());
		dto.setRemark(prItem.getRemark());
		return dto;
	}
	
	@Override
	public boolean sendEmailNotification( String prNo, User user ) {
		ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
		try {
			PurchaseRequisitionDto prDto = findByPrNo(prNo);
			
			this.generatePdfReportStream(prDto, pdfStream);
			
			List<String> to = new ArrayList<String>( buildEmailToList( user, prDto ) );
			
			String subject = "PR [ " + prDto.getPrNo() + " ] has been updated with status \"" + prDto.getStatus() + "\" by " + user.getFullName();
			String body = subject;
			String fileName = prDto.getPrNo() + ".pdf";
			
			return mailSender.sendMail(to, null, subject, body, pdfStream.toByteArray(), fileName);
		} catch (Exception e) {
			log.error("Error in sending email notifications....", e);
		} finally {
			try {
				pdfStream.close();
			} catch ( Exception e ) {
				log.error(e);
			}
		}
		return false;
	}
	
	private Set<String> buildEmailToList( User user, PurchaseRequisitionDto prDto ) {
		
		String status = prDto.getStatus();
		
		Set<String> emailTo = new HashSet<String>();
		emailTo.add(user.getEmail());
		if( user.getReportingTo() != null ) {
			emailTo.add(user.getReportingTo().getEmail());
		}
		
		Set<String> userTypes = new HashSet<String>();
		if ( status.equals(PurchaseRequisitionStatus.AUTHORIZED.getStatus()) ) {
			userTypes.add( UserProfileType.MANAGER.getUserProfileType() );
		} else if ( status.equals(PurchaseRequisitionStatus.APPROVED.getStatus()) ) {
			userTypes.add( UserProfileType.PURCHASE_MANAGER.getUserProfileType() );
			userTypes.add( UserProfileType.PURCHASE_SUPERVISOR.getUserProfileType() );
		} else if ( status.equals(PurchaseRequisitionStatus.ACKNOWLEDGED.getStatus()) ) {
			userTypes.add( UserProfileType.PURCHASE_MANAGER.getUserProfileType() );
			userTypes.add( UserProfileType.PURCHASE_SUPERVISOR.getUserProfileType() );
		}
		
		Set<Long> userIds = new HashSet<Long>();
		if( prDto.getCreatedBy().longValue() != user.getId().longValue() ) {
			userIds.add( prDto.getCreatedBy() );
		}
		
		if( !userTypes.isEmpty() ) {
			emailTo.addAll(userDao.getUserEmailsByTypes(userTypes));
		}
		
		if( !userIds.isEmpty() ) {
			emailTo.addAll(userDao.getUserEmailsByIds(userIds));
		}
		
		return emailTo;
	}

	@Override
	public void generatePdfReportStream(PurchaseRequisitionDto prDto, OutputStream pdfStream) throws Exception {
		InputStream jasperFileStream = null;
		try {
			Map<String, Object> reportParams = new HashMap<String, Object>();
			reportParams.put("prNo", prDto.getPrNo());
			reportParams.put("projectName", prDto.getProjectName());
			reportParams.put("projectCode", prDto.getProjectCode());
			reportParams.put("rev", prDto.getRev());
			reportParams.put("createdByName", prDto.getCreatedByName());
			reportParams.put("createdDateStr", prDto.getCreatedDateStr());
			reportParams.put("authorizedByName", prDto.getAuthorizedByName());
			reportParams.put("authorizedDateStr", prDto.getAuthorizedDateStr());
			reportParams.put("approvedByName", prDto.getApprovedByName());
			reportParams.put("approvedDateStr", prDto.getApprovedDateStr());
			reportParams.put("prRemark", prDto.getPrRemark());
			JRDataSource reportDataSource = new JRBeanCollectionDataSource(prDto.getPurchaseRequisionItems());
			jasperFileStream = this.getClass().getClassLoader().getResourceAsStream("IRY_PR_Report.jasper");
			JasperPrint jrPrint = JasperFillManager.fillReport(jasperFileStream, reportParams, reportDataSource);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jrPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, pdfStream);
			exporter.exportReport();
		} catch ( Exception e ) {
			log.error("Error in generating PDF Report...", e);
		} finally {
			try {
				if( jasperFileStream != null ) {
					jasperFileStream.close();
				}
			} catch ( Exception e ) {
				log.error(e);
			}
		}
	}
}
